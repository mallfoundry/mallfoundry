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

import org.mallfoundry.data.Query;
import org.mallfoundry.data.QueryBuilder;
import org.mallfoundry.util.ObjectBuilder;

public interface ProductSalesQuery extends Query, ObjectBuilder.ToBuilder<ProductSalesQuery.Builder> {

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    Short getYearStart();

    void setYearStart(Short yearStart);

    Byte getMonthStart();

    void setMonthStart(Byte monthStart);

    Byte getDayOfMonthStart();

    void setDayOfMonthStart(Byte dayOfMonthStart);

    Short getYearEnd();

    void setYearEnd(Short yearEnd);

    Byte getMonthEnd();

    void setMonthEnd(Byte monthEnd);

    Byte getDayOfMonthEnd();

    void setDayOfMonthEnd(Byte dayOfMonthEnd);

    interface Builder extends QueryBuilder<ProductSalesQuery, Builder> {

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder yearStart(Short yearStart);

        Builder monthStart(Byte monthStart);

        Builder dayOfMonthStart(Byte dayOfMonthStart);

        Builder yearEnd(Short yearEnd);

        Builder monthEnd(Byte monthEnd);

        Builder dayOfMonthEnd(Byte dayOfMonthEnd);
    }
}
