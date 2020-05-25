package com.mallfoundry.checkout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class InternalCheckoutItem implements CheckoutItem {

    private String productId;

    private String variantId;

    private int quantity;

    public static InternalCheckoutItem of(CheckoutItem item) {
        if (item instanceof InternalCheckoutItem) {
            return (InternalCheckoutItem) item;
        }

        var target = new InternalCheckoutItem();
        BeanUtils.copyProperties(item, target);
        return target;
    }

}
