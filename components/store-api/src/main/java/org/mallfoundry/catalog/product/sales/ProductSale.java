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

package org.mallfoundry.catalog.product.sales;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ProductSale extends Serializable, ObjectBuilder.ToBuilder<ProductSale.Builder> {

    ProductSaleId toId();

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    short getYear();

    void setYear(short year);

    byte getMonth();

    void setMonth(byte month);

    byte getDayOfMonth();

    void setDayOfMonth(byte dayOfMonth);

    BigDecimal getTotalAmounts();

    void setTotalAmounts(BigDecimal totalAmounts);

    void adjustTotalAmounts(BigDecimal deltaAmounts);

    long getTotalQuantities();

    void setTotalQuantities(long totalQuantities);

    void adjustTotalQuantities(long deltaQuantities);

    interface Builder extends ObjectBuilder<ProductSale> {

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder year(short year);

        Builder month(byte month);

        Builder dayOfMonth(byte dayOfMonth);

        Builder totalAmounts(BigDecimal totalAmounts);

        Builder totalQuantities(long totalQuantities);
    }
}
