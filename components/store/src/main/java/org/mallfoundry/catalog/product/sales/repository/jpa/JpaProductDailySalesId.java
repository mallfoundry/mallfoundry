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

package org.mallfoundry.catalog.product.sales.repository.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.product.sales.ProductDailySalesId;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaProductDailySalesId implements ProductDailySalesId, Serializable {

    private String productId;

    private String variantId;

    private int year;

    private int month;

    private int dayOfMonth;

    public static JpaProductDailySalesId of(ProductDailySalesId salesId) {
        if (salesId instanceof JpaProductDailySalesId) {
            return (JpaProductDailySalesId) salesId;
        }
        var target = new JpaProductDailySalesId();
        BeanUtils.copyProperties(salesId, target);
        return target;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaProductDailySalesId)) {
            return false;
        }
        JpaProductDailySalesId that = (JpaProductDailySalesId) object;
        return year == that.year
                && month == that.month
                && dayOfMonth == that.dayOfMonth
                && Objects.equals(productId, that.productId)
                && Objects.equals(variantId, that.variantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, variantId, year, month, dayOfMonth);
    }
}
