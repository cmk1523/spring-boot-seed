package com.techdevsolutions.dao.elasticsearch.user;

import com.techdevsolutions.beans.auditable.User;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class UserRowMapper {
    public static User MapToItem(Map<String, Object> map) throws Exception {
        if (map == null) { return null; }

        String id = (String) map.get("_id");
        Map<String, Object> source = (Map<String, Object>) map.get("_source");
        Map<String, Object> doc = (Map<String, Object>) source.get("doc");
        Map<String, Object> i = (doc == null) ? source : doc;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        User item = new User();
        item.setId(Integer.valueOf(id));
        item.setName((String) i.get("name"));
        item.setCreatedBy((String) i.get("createdBy"));
        String createdDateStr = (String ) i.get("createdDate");
        item.setCreatedDate(sdf.parse(createdDateStr).getTime());
        item.setUpdatedBy((String) i.get("updatedBy"));
        String updatedDateStr = (String ) i.get("updatedDate");
        item.setUpdatedDate(sdf.parse(updatedDateStr).getTime());
        return item;
    }

    public static Map<String, Object> ItemToMap(User item) {
        if (item == null) { return null; }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        map.put("id", item.getId());
        map.put("name", item.getName());
        map.put("createdBy", item.getCreatedBy());
        map.put("createdDate", sdf.format(new Date(item.getCreatedDate())));
        map.put("updatedBy", item.getUpdatedBy());
        map.put("updatedDate", sdf.format(new Date(item.getUpdatedDate())));
        return map;
    }

    public static List<User> MapToItems(List<Map<String, Object>> list) throws Exception {
        List<User> items = new ArrayList<>();

        for(Map<String, Object> item : list) {
            items.add(UserRowMapper.MapToItem(item));
        }

        return items;
    }
}
