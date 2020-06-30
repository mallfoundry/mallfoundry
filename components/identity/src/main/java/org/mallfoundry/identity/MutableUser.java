package org.mallfoundry.identity;

import java.util.Date;

public interface MutableUser {

    void setPassword(String password);

    void setCreatedTime(Date createdTime);
}
