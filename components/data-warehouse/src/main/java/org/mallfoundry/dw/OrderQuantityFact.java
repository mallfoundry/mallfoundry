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

package org.mallfoundry.dw;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class OrderQuantityFact {
    private String storeId;
    private String customerId;
    private String productId;
    private String variantId;
    private String statusId;
    private int quantity;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OrderQuantityFact)) {
            return false;
        }
        OrderQuantityFact that = (OrderQuantityFact) object;
        return Objects.equals(storeId, that.storeId)
                && Objects.equals(productId, that.productId)
                && Objects.equals(variantId, that.variantId)
                && Objects.equals(customerId, that.customerId)
                && Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, productId, variantId, customerId, statusId);
    }
}
