package com.mallfoundry.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class IndexBlobId implements Serializable {

    @Column(name = "bucket_")
    private String bucket;

    @Column(name = "path_")
    private String path;

    @Column(name = "value_")
    private String value;

    public IndexBlobId(String bucket, String path, String value) {
        this.setBucket(bucket);
        this.setPath(path);
        this.setValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexBlobId that = (IndexBlobId) o;
        return Objects.equals(bucket, that.bucket) &&
                Objects.equals(path, that.path) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucket, path, value);
    }
}
