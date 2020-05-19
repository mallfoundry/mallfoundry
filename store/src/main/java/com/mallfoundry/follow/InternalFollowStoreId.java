package com.mallfoundry.follow;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class InternalFollowStoreId implements Serializable {

    private String followerId;

    private String id;

    public InternalFollowStoreId(String followerId, String id) {
        this.followerId = followerId;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalFollowStoreId that = (InternalFollowStoreId) o;
        return Objects.equals(followerId, that.followerId) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, id);
    }
}
