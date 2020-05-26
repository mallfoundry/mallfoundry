package org.mallfoundry.follow;

import org.mallfoundry.data.PageableSupport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalFollowProductQuery extends PageableSupport implements FollowProductQuery {
    private String followerId;
}
