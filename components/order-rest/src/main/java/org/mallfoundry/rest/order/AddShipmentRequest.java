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
import org.mallfoundry.order.Shipment;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AddShipmentRequest extends ShipmentRequest {

    private List<ShipmentItemRequest> items;

    @Override
    public Shipment assignToShipment(Shipment shipment) {
        var items = this.items.stream()
                .map(request ->
                        shipment.createItem(null).toBuilder()
                                .productId(request.getProductId())
                                .variantId(request.getVariantId())
                                .quantity(request.getQuantity())
                                .name(request.getName())
                                .imageUrl(request.getImageUrl()).build())
                .collect(Collectors.toUnmodifiableList());
        return super.assignToShipment(shipment).toBuilder().items(items).build();
    }


    @Getter
    @Setter
    public static class ShipmentItemRequest {
        private String productId;
        private String variantId;
        private int quantity;
        private String name;
        private String imageUrl;
    }

}
