package org.mallfoundry.order;

import java.math.BigDecimal;

public interface RefundItem {

    String getItemId();

    void setItemId(String itemId);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    String getReason();

    void setReason(String reason);
}
