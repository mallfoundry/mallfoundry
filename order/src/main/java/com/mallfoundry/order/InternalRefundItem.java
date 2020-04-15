package com.mallfoundry.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InternalRefundItem implements RefundItem {
    private String itemId;

    private BigDecimal amount;

    private String reason;
}
