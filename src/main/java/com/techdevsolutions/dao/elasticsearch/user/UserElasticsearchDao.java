package com.techdevsolutions.dao.elasticsearch.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdevsolutions.beans.Filter;
import com.techdevsolutions.beans.Search;
import com.techdevsolutions.beans.auditable.User;
import com.techdevsolutions.dao.elasticsearch.BaseElasticsearchDao;
import com.techdevsolutions.dao.elasticsearch.ElasticsearchRestClient;
import com.techdevsolutions.service.CrudServiceInterface;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserElasticsearchDao extends BaseElasticsearchDao implements CrudServiceInterface<User> {
    private static final String INDEX = "users";

    @Autowired
    public UserElasticsearchDao(ElasticsearchRestClient elasticsearchRestClient, Environment environment) {
        super(elasticsearchRestClient, environment);
    }

    @Override
    public List<User> search(Search search) throws Exception {
        return this.getAll();
    }

    @Override
    public List<User> get(Filter search) throws Exception {
        return this.getAll();
    }

    public Integer getNextId() throws Exception {
        String command = UserElasticsearchDao.INDEX + "/doc/_search";
        HttpEntity httpEntity = new NStringEntity("{ \"sort\": [ { \"_id\": { \"order\": \"desc\" } } ], \"size\": 1 }", ContentType.APPLICATION_JSON);

        try {
            Response response = this.getClient().performRequest(BaseElasticsearchDao.HTTP_GET, command, this.getPrettyParams(), httpEntity);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new Exception("Unable to get next ID. " +
                        "Response Code: " + response.getStatusLine().getStatusCode() + ". " +
                        "Response: " + EntityUtils.toString(response.getEntity()));
            }

            return this.getId(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        String command = UserElasticsearchDao.INDEX + "/doc/_search";
        Response response = this.getClient().performRequest(BaseElasticsearchDao.HTTP_GET, command, this.getPrettyParams());

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("Unable to get all users. " +
                    "Response Code: " + response.getStatusLine().getStatusCode() + ". " +
                    "Response: " + EntityUtils.toString(response.getEntity()));
        }

        return UserRowMapper.MapToItems(this.getHits(EntityUtils.toString(response.getEntity())));
    }

    @Override
    public User get(Integer id) throws Exception {
        String command = UserElasticsearchDao.INDEX + "/doc/" + id;
        Response response = this.getClient().performRequest(BaseElasticsearchDao.HTTP_GET, command, this.getPrettyParams());

        if (response.getStatusLine().getStatusCode() == 404) {
            return null;
        } else if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("Unable to get user: " + id + ". " +
                    "Response Code: " + response.getStatusLine().getStatusCode() + ". " +
                    "Response: " + EntityUtils.toString(response.getEntity()));
        }

        return UserRowMapper.MapToItem(this.getHit(EntityUtils.toString(response.getEntity())));
    }

    @Override
    public User create(User item) throws Exception {
        item.setId(this.getNextId());
        String itemAsStr = new ObjectMapper().writeValueAsString(UserRowMapper.ItemToMap(item));

        String command = UserElasticsearchDao.INDEX + "/doc/" + item.getId();
        HttpEntity httpEntity = new NStringEntity(itemAsStr, ContentType.APPLICATION_JSON);
        Response response = this.getClient().performRequest(BaseElasticsearchDao.HTTP_PUT, command, this.getPrettyParams(), httpEntity);

        if (response.getStatusLine().getStatusCode() != 201) {
            throw new Exception("Unable to create user: " + item.getName() + ". " +
                    "Response Code: " + response.getStatusLine().getStatusCode() + ". " +
                    "Response: " + EntityUtils.toString(response.getEntity()));
        }

        return this.get(item.getId());
    }

    @Override
    public void remove(Integer id) throws Exception {
        User item = this.get(id);
        item.setRemoved(true);
        this.update(item);
    }

    @Override
    public void delete(Integer id) throws Exception {
        String command = UserElasticsearchDao.INDEX + "/doc/" + id;
        Response response = this.getClient().performRequest(BaseElasticsearchDao.HTTP_DELETE, command, this.getPrettyParams());

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("Unable to delete user: " + id + ". " +
                    "Response Code: " + response.getStatusLine().getStatusCode() + ". " +
                    "Response: " + EntityUtils.toString(response.getEntity()));
        }
    }

    @Override
    public User update(User item) throws Exception {
        String itemAsStr = new ObjectMapper().writeValueAsString(UserRowMapper.ItemToMap(item));
        itemAsStr = " { \"doc\": " + itemAsStr + " } ";

        String command = UserElasticsearchDao.INDEX + "/doc/" + item.getId();
        HttpEntity httpEntity = new NStringEntity(itemAsStr, ContentType.APPLICATION_JSON);
        Response response = this.getClient().performRequest(BaseElasticsearchDao.HTTP_POST, command, this.getPrettyParams(), httpEntity);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("Unable to update user: " + item.getId() + ". " +
                    "Response Code: " + response.getStatusLine().getStatusCode() + ". " +
                    "Response: " + EntityUtils.toString(response.getEntity()));
        }

        return item;
    }
}
