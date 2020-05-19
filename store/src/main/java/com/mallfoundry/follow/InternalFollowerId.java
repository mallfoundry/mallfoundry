package com.mallfoundry.follow;

public class InternalFollowerId implements FollowerId {

    private final String id;

    public InternalFollowerId(String id) {
        this.id = id;
    }

    @Override
    public String getIdentifier() {
        return this.id;
    }


}
