package com.mallfoundry.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class InternalFollowProductId implements Serializable {

    private String followerId;

    private String id;

    public InternalFollowProductId(String followerId, String id) {
        this.followerId = followerId;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalFollowProductId that = (InternalFollowProductId) o;
        return Objects.equals(followerId, that.followerId) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, id);
    }
}
