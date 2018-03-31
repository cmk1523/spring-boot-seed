package com.techdevsolutions.beans.auditable;

import com.techdevsolutions.beans.ValidationResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class Auditable implements Serializable {
    private Integer id = 0;
    private String name = "";
    private String createdBy = "";
    private String updatedBy = "";
    private Long createdDate = 0L;
    private Long updatedDate = 0L;

    public static ValidationResponse Validate(Auditable i) {
        if (i == null) {
            return new ValidationResponse(false, "", "Auditable is null");
        }

        if (i.getId() == null || i.getId() == 0) {
            return new ValidationResponse(false, "id", "Auditable id is null or not set");
        }

        if (StringUtils.isEmpty(i.getName())) {
            return new ValidationResponse(false, "name", "Auditable name is empty");
        }

        if (StringUtils.isEmpty(i.getCreatedBy())) {
            return new ValidationResponse(false, "createdBy", "Auditable createdBy is empty");
        }

        if (i.getCreatedDate() == null || i.getCreatedDate() == 0L) {
            return new ValidationResponse(false, "createdDate", "Auditable createdDate null or not set");
        }

        if (StringUtils.isEmpty(i.getUpdatedBy())) {
            return new ValidationResponse(false, "updatedBy", "Auditable updatedBy is empty");
        }

        if (i.getUpdatedDate() == null || i.getUpdatedDate() == 0L) {
            return new ValidationResponse(false, "updatedDate", "Auditable updatedDate null or not set");
        }

        return new ValidationResponse(true, "", "");
    }

    public Auditable() {
    }

    public Auditable(Integer id, String name, String createdBy, String updatedBy, Long createdDate, Long updatedDate) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "BaseItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auditable baseItem = (Auditable) o;
        return Objects.equals(id, baseItem.id) &&
                Objects.equals(name, baseItem.name) &&
                Objects.equals(createdBy, baseItem.createdBy) &&
                Objects.equals(updatedBy, baseItem.updatedBy) &&
                Objects.equals(createdDate, baseItem.createdDate) &&
                Objects.equals(updatedDate, baseItem.updatedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, createdBy, updatedBy, createdDate, updatedDate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }
}
