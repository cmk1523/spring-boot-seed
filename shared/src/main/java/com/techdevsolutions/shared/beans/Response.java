package com.techdevsolutions.shared.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Response implements Serializable {
    protected Object data = null;
    protected Long took = 0L;
    protected String username;
    protected Long date;
    protected String dateAsString;

    public Response() {
    }

    public Response(Object data, Long took, String username, Date date) {
        this.data = data;
        this.took = took;
        this.username = username;
        this.date = date.getTime();

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        this.dateAsString = df.format(new Date());
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTook() {
        return took;
    }

    public void setTook(Long took) {
        this.took = took;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDateAsString() {
        return dateAsString;
    }

    public void setDateAsString(String dateAsString) {
        this.dateAsString = dateAsString;
    }
}
