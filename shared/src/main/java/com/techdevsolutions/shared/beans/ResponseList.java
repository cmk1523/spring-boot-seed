package com.techdevsolutions.shared.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ResponseList extends Response implements Serializable {
    private Integer size = 0;

    public ResponseList(List data, Long took, String username, Date date) {
        this.setData(data);
        this.setSize(data.size());
        this.setTook(took);

        this.username = username;
        this.date = date.getTime();

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        this.dateAsString = df.format(new Date());
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
