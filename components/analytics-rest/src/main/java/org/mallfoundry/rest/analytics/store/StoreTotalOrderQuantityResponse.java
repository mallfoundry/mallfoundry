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

package org.mallfoundry.rest.analytics.store;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.analytics.store.StoreTotalOrderQuantity;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class StoreTotalOrderQuantityResponse {
    private int pendingQuantity;
    private int awaitingPaymentQuantity;
    private int awaitingFulfillmentQuantity;
    private int awaitingShipmentQuantity;
    private int partiallyShippedQuantity;
    private int shippedQuantity;
    private int awaitingPickupQuantity;
    private int partiallyRefundedQuantity;
    private int refundedQuantity;
    private int disputedQuantity;
    private int cancelledQuantity;
    private int completedQuantity;
    private int declinedQuantity;

    public static StoreTotalOrderQuantityResponse of(StoreTotalOrderQuantity quantity) {
        var response = new StoreTotalOrderQuantityResponse();
        BeanUtils.copyProperties(quantity, response);
        return response;
    }
}