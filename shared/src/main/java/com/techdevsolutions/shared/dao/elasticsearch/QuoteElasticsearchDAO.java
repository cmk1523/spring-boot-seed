package com.techdevsolutions.shared.dao.elasticsearch;

import com.techdevsolutions.shared.beans.yahoo.Quote;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class QuoteElasticsearchDAO extends BaseElasticsearchHighLevel {
    public static String host = "192.168.1.146";
    public static String index = "stock-quotes";
    protected QuoteRowMapper rowMapper = new QuoteRowMapper();

    @Autowired
    public QuoteElasticsearchDAO() {
        
    }

    public Quote get(String id) throws Exception {
        String quoteAsString = this.getDocument(id, QuoteElasticsearchDAO.index);
        return this.rowMapper.fromJson(quoteAsString);
    }

    public void create(Quote quote) throws Exception {
        String quoteAsJson = this.rowMapper.toJson(quote);
        this.createDocument(quoteAsJson, quote.getId().toLowerCase(), QuoteElasticsearchDAO.index);
    }

    public void createBulk(List<Quote> quotes) throws Exception {
        quotes.forEach((quote)->{
            try {
                String quoteAsJson = this.rowMapper.toJson(quote);
                IndexRequest indexRequest = new IndexRequest(QuoteElasticsearchDAO.index).id(quote.getId().toLowerCase());
                indexRequest.source(quoteAsJson, XContentType.JSON);
                this.getBulkProcessor().add(indexRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void delete(Quote quote) throws Exception {
        this.deleteDocument(quote.getId(), QuoteElasticsearchDAO.index);
    }

    public void setup() throws IOException {
        this.getClient(QuoteElasticsearchDAO.host);

        try {
            this.createIndex(QuoteElasticsearchDAO.index);
        } catch (ElasticsearchStatusException e) {
            if (!e.getDetailedMessage().contains("resource_already_exists_exception")) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
