package com.mallfoundry.follow;

import java.io.Serializable;
import java.util.Date;

public interface FollowStore extends Serializable {

    String getId();

    String getLogoUrl();

    String getName();

    String getFollowerId();

    Date getFollowTime();
}
