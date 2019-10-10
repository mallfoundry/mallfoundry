package com.mallfoundry.buyer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A buyer's purchase order class.
 */
@Getter
@Setter
public class PurchaseOrder {

    /**
     * Id of purchase order.
     */
    private String id;

    /**
     * Buyer's shopping cart
     */
    private String cart;

    /**
     * Purchased product identification.
     */
    @JsonProperty("product_id")
    private String productId;

    /**
     * Purchased product specifications.
     */
    private List<Integer> specs;

    /**
     * Purchase quantity of purchase order.
     */
    private int quantity;

    private Date createTime;

    public void setCreateTimeIfNull() {
        if (Objects.isNull(this.getCreateTime())) {
            this.setCreateTime(new Date());
        }
    }
}
