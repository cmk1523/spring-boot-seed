package com.techdevsolutions.shared.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdevsolutions.shared.beans.CollectionEvent;
import com.techdevsolutions.shared.dao.elasticsearch.BaseElasticsearchHighLevel;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class RandomCollectionEventSerivceTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    RandomService randomService = new RandomService();

    @Test
    public void getRandomCollectionEvent() throws Exception {
        Integer numOfIterations = 1000;
        List<CollectionEvent> items = new ArrayList<>();
        for (int i = 0; i < numOfIterations; i++) {
            CollectionEvent collectionEvent = RandomService.getRandomCollectionEvent();
            logger.info(collectionEvent.toString());
            items.add(collectionEvent);
        }

        Map<String, List<CollectionEvent>> map = new HashMap<>();
        items.forEach((i)->map.putIfAbsent(i.getSelectorType(), new ArrayList<>()));
        items.forEach((i)->map.get(i.getSelectorType()).add(i));

        BaseElasticsearchHighLevel baseElasticsearchHighLevel = new BaseElasticsearchHighLevel();
        RestHighLevelClient client = baseElasticsearchHighLevel.getClient();

        String index = "collection-events";
        baseElasticsearchHighLevel.deleteIndex(client, index);

        BulkRequest request = new BulkRequest();
        ObjectMapper objectMapper = new ObjectMapper();

        items.forEach((i)->{
            try {
                IndexRequest indexRequest = new IndexRequest();
                String jsonString = objectMapper.writeValueAsString(i);
                indexRequest.source(jsonString, XContentType.JSON);
                indexRequest.index(index);
                indexRequest.id(i.getId());

                request.add(indexRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);

    }

}
