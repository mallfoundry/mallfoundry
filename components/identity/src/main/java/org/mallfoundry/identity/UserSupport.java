package org.mallfoundry.identity;

import java.util.Date;

public abstract class UserSupport implements User, MutableUser {

    @Override
    public void changePassword(String password) {
        this.setPassword(password);
    }

    @Override
    public void create() {
        this.setCreatedTime(new Date());
    }
}
