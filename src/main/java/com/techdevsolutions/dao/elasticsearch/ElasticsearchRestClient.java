package com.techdevsolutions.dao.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchRestClient {
    private static final Integer TIMEOUT = 30000;
    private static RestClient restClient;

    @Autowired
    public ElasticsearchRestClient() {
    }

    public static RestClient getClient(Environment environment) {
        if (ElasticsearchRestClient.restClient == null) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(environment.getProperty("elasticsearch.username"), environment.getProperty("elasticsearch.password")));

            RestClientBuilder restClientBuilder = RestClient.builder(
                    new HttpHost(environment.getProperty("elasticsearch.host"), 9200, "http"));
            restClientBuilder.setMaxRetryTimeoutMillis(ElasticsearchRestClient.TIMEOUT);
            restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
            });
            ElasticsearchRestClient.restClient = restClientBuilder.build();
        }

        return ElasticsearchRestClient.restClient;
    }
}
