package com.techdevsolutions.files.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.auditable.Auditable;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class File extends Auditable implements Serializable, Comparable<File> {
    private String userId = "";
    private String path = "";
    private Long size = 0L;
    private String hash = "";
    private String contentType = "";

    public File() {
    }

    public static ValidationResponse Validate(File i) {
        return File.Validate(i, false);
    }

    public static ValidationResponse Validate(File i, Boolean isNew) {
        if (!Auditable.Validate(i, isNew).getValid()) {
            return Auditable.Validate(i, isNew);
        }

        if (StringUtils.isEmpty(i.getUserId())) {
            return new ValidationResponse(false, "userId", "File userId is empty");
        }

        if (StringUtils.isEmpty(i.getHash())) {
            return new ValidationResponse(false, "hash", "File hash is empty");
        }

        return new ValidationResponse(true, "", "");
    }

    @Override
    public String toString() {
        return "File{" +
                "userId='" + userId + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", hash='" + hash + '\'' +
                ", contentType='" + contentType + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;
        if (!super.equals(o)) return false;
        File file = (File) o;
        return Objects.equals(userId, file.userId) &&
                Objects.equals(path, file.path) &&
                Objects.equals(size, file.size) &&
                Objects.equals(hash, file.hash) &&
                Objects.equals(contentType, file.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, path, size, hash, contentType);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public int compareTo(File o) {
        return this.getId().compareTo(o.getId());
    }
}
