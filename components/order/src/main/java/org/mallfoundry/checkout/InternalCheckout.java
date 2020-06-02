package org.mallfoundry.checkout;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.order.Order;
import org.mallfoundry.shipping.Address;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InternalCheckout implements Checkout {

    private String cartId;

    private Address shippingAddress;

    private List<CheckoutItem> items = new ArrayList<>();

    private List<Order> orders = new ArrayList<>();

    public static InternalCheckout of(Checkout checkout) {
        if (checkout instanceof InternalCheckout) {
            return (InternalCheckout) checkout;
        }
        var target = new InternalCheckout();
        BeanUtils.copyProperties(checkout, target);
        return target;
    }

    @Override
    public CheckoutItem createItem() {
        return new InternalCheckoutItem();
    }

    @Override
    public void addItem(CheckoutItem item) {
        this.items.add(item);
    }

    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getOrders().stream()
                .map(Order::getSubtotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
