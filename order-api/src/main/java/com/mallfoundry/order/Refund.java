package com.mallfoundry.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Refund {

    String getId();

    String getOrderId();

    List<RefundItem> getItems();

    BigDecimal getTotalAmount();

    Date getCreatedTime();

}
