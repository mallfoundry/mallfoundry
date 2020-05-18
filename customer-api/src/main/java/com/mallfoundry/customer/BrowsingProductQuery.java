package com.mallfoundry.customer;

import java.util.Date;

public interface BrowsingProductQuery {

    String getCustomerId();

    void setCustomerId(String customerId);

    Date getBrowsingTime();

    void setBrowsingTime(Date browsingTime);
}
