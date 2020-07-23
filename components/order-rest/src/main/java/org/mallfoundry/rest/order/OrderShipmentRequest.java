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

package org.mallfoundry.rest.order;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.order.OrderShipment;
import org.mallfoundry.shipping.CarrierCode;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderShipmentRequest {
    private CarrierCode shippingProvider;
    private String shippingMethod;
    private String trackingNumber;

    public OrderShipment assignTo(OrderShipment shipment) {
        return shipment.toBuilder()
                .shippingProvider(this.shippingProvider)
                .shippingMethod(this.shippingMethod)
                .trackingNumber(this.trackingNumber).build();
    }

    @Getter
    @Setter
    public static class AddOrderShipmentRequest extends OrderShipmentRequest {
        private List<OrderShipmentItemRequest> items;

        @Override
        public OrderShipment assignTo(OrderShipment shipment) {
            var items = this.items.stream()
                    .map(request ->
                            shipment.createItem(null).toBuilder()
                                    .productId(request.getProductId())
                                    .variantId(request.getVariantId())
                                    .quantity(request.getQuantity())
                                    .name(request.getName())
                                    .imageUrl(request.getImageUrl()).build())
                    .collect(Collectors.toUnmodifiableList());
            return super.assignTo(shipment).toBuilder().items(items).build();
        }
    }
}
