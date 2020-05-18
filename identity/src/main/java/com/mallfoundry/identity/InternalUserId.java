package com.mallfoundry.identity;

public class InternalUserId implements UserId {
    private final String id;

    public InternalUserId(String id) {
        this.id = id;
    }

    @Override
    public String getIdentifier() {
        return this.id;
    }
}
