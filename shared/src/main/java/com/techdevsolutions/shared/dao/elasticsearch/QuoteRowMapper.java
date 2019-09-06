package com.techdevsolutions.shared.dao.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdevsolutions.shared.beans.yahoo.Quote;
import com.techdevsolutions.shared.service.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class QuoteRowMapper {
    private ObjectMapper objectMapper = new ObjectMapper();

    public Quote fromJson(String json) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.ISO_STRING);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        Map<String, Object> map = this.objectMapper.readValue(json, Map.class);
        map.put("date", sdf.parse((String) map.get("date")));
        map.put("createdDate", sdf.parse((String) map.get("createdDate")));
        map.put("updatedDate", sdf.parse((String) map.get("updatedDate")));
        return this.objectMapper.convertValue(map, Quote.class);
    }

    public String toJson(Quote object) throws JsonProcessingException {
        try {
            Map<String, Object> map = this.objectMapper.convertValue(object, Map.class);
            map.put("date", DateUtils.DateToISO((Long) map.get("date")));
            map.put("createdDate", DateUtils.DateToISO((Long) map.get("createdDate")));
            map.put("updatedDate", DateUtils.DateToISO((Long) map.get("updatedDate")));
            return this.objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
