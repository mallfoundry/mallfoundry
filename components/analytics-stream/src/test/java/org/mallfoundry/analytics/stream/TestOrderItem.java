package org.mallfoundry.analytics.stream;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.order.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class TestOrderItem implements OrderItem {
    private String id;
    private String storeId;
    private String productId;
    private String variantId;
    private String imageUrl;
    private String name;
    private List<OptionSelection> optionSelections;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountAmount;
    private BigDecimal shippingCost;
    private BigDecimal discountShippingCost;


    public BigDecimal getDiscountAmount() {
        return Objects.isNull(this.discountAmount)
                ? BigDecimal.ZERO
                : this.discountAmount;
    }

    public BigDecimal getShippingCost() {
        return Objects.isNull(this.shippingCost)
                ? BigDecimal.ZERO
                : this.shippingCost;
    }

    public BigDecimal getDiscountShippingCost() {
        return Objects.isNull(this.discountShippingCost)
                ? BigDecimal.ZERO
                : this.discountShippingCost;
    }
    @Override
    public BigDecimal getTotalPrice() {
        return this.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getTotalPrice().add(this.getShippingCost());
    }

    @Override
    public BigDecimal getTotalAmount() {
        return this.getSubtotalAmount()
                .add(this.getDiscountAmount())
                .add(this.getDiscountShippingCost());
    }

}
