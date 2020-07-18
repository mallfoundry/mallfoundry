/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.rest.checkout;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.checkout.Checkout;
import org.mallfoundry.order.OrderSource;
import org.mallfoundry.rest.shipping.AddressRequest;
import org.mallfoundry.shipping.DefaultAddress;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CheckoutRequest {
    private String cartId;
    private AddressRequest shippingAddress;
    private List<CheckoutItemRequest> items;
    private OrderSource source;

    public Checkout assignToCheckout(Checkout checkout) {
        if (Objects.nonNull(this.shippingAddress)) {
            var address = new DefaultAddress();
            this.shippingAddress.assignToAddress(address);
            checkout.setShippingAddress(address);
        }
        checkout.setCartId(this.cartId);
        checkout.setSource(this.source);
        if (Objects.nonNull(items)) {
            this.getItems()
                    .stream()
                    .map(item -> checkout.createItem().toBuilder()
                            .productId(item.getProductId()).variantId(item.getVariantId())
                            .quantity(item.getQuantity()).build())
                    .forEach(checkout::addItem);
        }
        return checkout;
    }
}
