package com.techdevsolutions.shared.beans;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class CollectionEvent implements Serializable {
    private String id = "";
    private String selector = "";
    private String selectorType = "";
    private String selectorTyped = "";
    private Date date = null;
    private String dateStr = null;
    private String type = "";
    private String subType = "";
    private Double latitude = null;
    private Double longitude = null;
    private String latitudeLongitude = null;
    private String data = "";

    public CollectionEvent() {
    }

    public static ValidationResponse Validate(CollectionEvent i) {
        if (i == null) {
            return new ValidationResponse(false, "", "item is null");
        }

        if (StringUtils.isEmpty(i.getSelector())) {
            return new ValidationResponse(false, "selector", "selector is empty");
        }

        if (StringUtils.isEmpty(i.getSelectorType())) {
            return new ValidationResponse(false, "selectorType", "selectorType is empty");
        }

        if (i.getDate() == null) {
            return new ValidationResponse(false, "date", "date is null");
        }

        return new ValidationResponse(true);
    }

    @Override
    public String toString() {
        return "CollectionEvent{" +
                "id='" + id + '\'' +
                ", selector='" + selector + '\'' +
                ", selectorType='" + selectorType + '\'' +
                ", selectorTyped='" + selectorTyped + '\'' +
                ", date=" + date +
                ", dateStr='" + dateStr + '\'' +
                ", type='" + type + '\'' +
                ", subType='" + subType + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", latitudeLongitude='" + latitudeLongitude + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionEvent)) return false;
        CollectionEvent that = (CollectionEvent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(selector, that.selector) &&
                Objects.equals(selectorType, that.selectorType) &&
                Objects.equals(selectorTyped, that.selectorTyped) &&
                Objects.equals(date, that.date) &&
                Objects.equals(dateStr, that.dateStr) &&
                Objects.equals(type, that.type) &&
                Objects.equals(subType, that.subType) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(latitudeLongitude, that.latitudeLongitude) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, selector, selectorType, selectorTyped, date, dateStr, type, subType, latitude, longitude, latitudeLongitude, data);
    }

    public String getDateStr() {
        return dateStr;
    }

    public String getData() {
        return data;
    }

    public CollectionEvent setData(String data) {
        this.data = data;
        return this;
    }

    public String getId() {
        return id;
    }

    public CollectionEvent setId(String id) {
        this.id = id;
        return this;
    }

    public String getSelector() {
        return selector;
    }

    public CollectionEvent setSelector(String selector) {
        this.selector = selector;

        if (this.getSelectorType() != null) {
            this.selectorTyped = this.selector + "<" + this.getSelectorType() + ">";
        }

        return this;
    }

    public String getSelectorType() {
        return selectorType;
    }

    public CollectionEvent setSelectorType(String selectorType) {
        this.selectorType = selectorType;

        if (this.getSelector() != null) {
            this.selectorTyped = this.getSelector() + "<" + this.selectorType + ">";
        }

        return this;
    }

    public String getSelectorTyped() {
        return selectorTyped;
    }

    public CollectionEvent setSelectorTyped(String selectorTyped) {
        this.selectorTyped = selectorTyped;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public CollectionEvent setDate(Date date) {
        this.date = date;
        this.dateStr = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        return this;
    }

    public String getType() {
        return type;
    }

    public CollectionEvent setType(String type) {
        this.type = type;
        return this;
    }

    public String getSubType() {
        return subType;
    }

    public CollectionEvent setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public CollectionEvent setLatitude(Double latitude) {
        this.latitude = latitude;
        this.latitudeLongitude = this.latitude + "," + this.longitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public CollectionEvent setLongitude(Double longitude) {
        this.longitude = longitude;
        this.latitudeLongitude = this.latitude + "," + this.longitude;
        return this;
    }

    public String getLatitudeLongitude() {
        return latitudeLongitude;
    }
}
