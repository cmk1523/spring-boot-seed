package com.techdevsolutions.shared.dao.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;

public class BaseElasticsearchHighLevel {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected RestHighLevelClient client = null;
    protected BulkProcessor bulkProcessor = null;

    public RestHighLevelClient getClient() {
        return this.getClient("localhost");
    }

    public RestHighLevelClient getClient(String host) {
        if (this.client == null) {
            this.client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(host, 9200, "http")));
        }

        return this.client;
    }

    public BulkProcessor getBulkProcessor() {
        return this.getBulkProcessor("localhost");
    }

    public BulkProcessor getBulkProcessor(String host) {
        if (this.bulkProcessor == null) {
            BulkProcessor.Listener listener = new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId, BulkRequest request) {
                    logger.debug("beforeBulk... " +
                            "items: " + request.numberOfActions() + ", " +
                            "estimatedSizeInBytes: " + request.estimatedSizeInBytes()
                    );
                }

                @Override
                public void afterBulk(long executionId, BulkRequest request,
                                      BulkResponse response) {
                    logger.debug("afterBulk... " +
                            "response: " + response.getIngestTookInMillis() + " ms"
                    );
                }

                @Override
                public void afterBulk(long executionId, BulkRequest request,
                                      Throwable failure) {
                    logger.error("afterBulk... failure: " + failure.toString());
                }
            };

            BulkProcessor.Builder builder = BulkProcessor.builder((request, bulkListener) ->
                            this.getClient(host).bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                    listener);
            builder.setBulkActions(10000);
            builder.setBulkSize(new ByteSizeValue(5L, ByteSizeUnit.MB));
            builder.setConcurrentRequests(1);
            builder.setFlushInterval(TimeValue.timeValueSeconds(1L));
            builder.setBackoffPolicy(BackoffPolicy
                    .constantBackoff(TimeValue.timeValueSeconds(1L), 3));
            this.bulkProcessor = builder.build();
        }

        return this.bulkProcessor;
    }

    protected void createIndex(String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        this.getClient().indices().create(request, RequestOptions.DEFAULT);
    }

    public void deleteIndex(RestHighLevelClient client, String index) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        this.getClient().indices().delete(request, RequestOptions.DEFAULT);
    }

    protected String getDocument(String id, String index) throws Exception {
        GetRequest request = new GetRequest(index, id);

        try {
            GetResponse response = this.getClient().get(request, RequestOptions.DEFAULT);
            return response.getSourceAsString();
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                throw new Exception("Item not found with id: " + id);
            } else {
                throw new Exception("Error: " + e.getDetailedMessage());
            }
        }
    }

    protected void createDocument(String objectAsString, String index) throws Exception {
        this.createDocument(objectAsString, Integer.valueOf(objectAsString.hashCode()).toString(), index);
    }

    protected void createDocument(String objectAsString, String id, String index) throws Exception {
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.source(objectAsString, XContentType.JSON);
        IndexResponse response = this.getClient().index(request, RequestOptions.DEFAULT);

        if (!response.status().toString().equalsIgnoreCase("CREATED") &&
                !response.status().toString().equalsIgnoreCase("OK")) {
            throw new Exception("Unable to create item: " + response.status());
        }
    }

    protected void deleteDocument(String id, String index) throws Exception {
        DeleteRequest request = new DeleteRequest(index);
        request.id(id);
        DeleteResponse response = this.getClient().delete(request, RequestOptions.DEFAULT);

        if (!response.status().toString().equalsIgnoreCase("OK")) {
            throw new Exception("Unable to delete item by id " + id + ": " + response.status());
        }
    }

    protected BulkProcessor getBulkProcessor(Client client) {
        BulkProcessor bulkProcessor = BulkProcessor.builder(client,
            new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId,
                                       BulkRequest request) {

                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      BulkResponse response) {

                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      Throwable failure) {

                }
            })
            .setBulkActions(10000)
            .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
            .setFlushInterval(TimeValue.timeValueSeconds(5))
            .setConcurrentRequests(1)
            .setBackoffPolicy(
                    BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
            .build();
        return bulkProcessor;
    }


}

