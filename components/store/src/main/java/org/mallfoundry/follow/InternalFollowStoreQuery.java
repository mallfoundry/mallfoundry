package org.mallfoundry.follow;

import org.mallfoundry.data.PageableSupport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalFollowStoreQuery extends PageableSupport implements FollowStoreQuery {
    private String followerId;
}
