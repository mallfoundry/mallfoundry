package com.mallfoundry.marketing;

import java.util.Date;

public interface Coupon {

    String getId();

    String getName();

    void setName(String name);

    String getCode();

    void setCode(String code);

    int getUses();

    int getMaxUsers();

    void setMaxUsers(int maxUsers);

    int getMaxUsesPerCustomer();

    void setMaxUsesPerCustomer(int maxUsesPerCustomer);

    int getExpires();

    void setExpires(int expires);

    boolean isEnabled();

    void enable();

    void disable();

    Date getCreatedTime();
}
