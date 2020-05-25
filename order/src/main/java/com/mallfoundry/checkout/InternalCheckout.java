package com.mallfoundry.checkout;

import com.mallfoundry.order.Order;
import com.mallfoundry.shipping.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InternalCheckout implements Checkout {

    private String cartId;

    private Address shippingAddress;

    private List<CheckoutItem> items = new ArrayList<>();

    private List<Order> orders;

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
}
