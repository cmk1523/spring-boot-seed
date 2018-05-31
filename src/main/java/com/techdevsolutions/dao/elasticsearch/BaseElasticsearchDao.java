package com.techdevsolutions.dao.elasticsearch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BaseElasticsearchDao {
    protected Logger logger = Logger.getLogger(BaseElasticsearchDao.class.getName());
    protected ElasticsearchRestClient elasticsearchRestClient;
    protected Environment environment;
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_GET = "GET";
    public static final String HTTP_DELETE = "DELETE";
    public static final String HTTP_POST = "POST";

    @Autowired
    public BaseElasticsearchDao(ElasticsearchRestClient elasticsearchRestClient, Environment environment) {
        this.elasticsearchRestClient = elasticsearchRestClient;
        this.environment = environment;
    }

    protected RestClient getClient() {
        return ElasticsearchRestClient.getClient(this.environment);
    }

    protected Map<String, String> getPrettyParams() {
        return Collections.singletonMap("pretty", "true");
    }

    protected List<Map<String, Object>> getHits(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(responseBody, new TypeReference<HashMap<String, Object>>() {});
        Map<String, Object> hits = (Map<String, Object>) map.get("hits");
        return (List<Map<String, Object>>) hits.get("hits");
    }

    protected Map<String, Object> getHit(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, new TypeReference<HashMap<String, Object>>() {});
    }

    protected Integer getId(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(responseBody, new TypeReference<HashMap<String, Object>>() {});
        Map<String, Object> hits = (Map<String, Object>) map.get("hits");
        List<Map<String, Object>>  list = (List<Map<String, Object>>) hits.get("hits");

        if (list.size() > 0) {
            return Integer.valueOf((String) list.get(0).get("_id")) + 1;
        } else {
            return 1;
        }
    }
}
