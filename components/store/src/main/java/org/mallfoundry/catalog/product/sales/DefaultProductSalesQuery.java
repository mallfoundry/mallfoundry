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

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;

@Getter
@Setter
public class DefaultProductSalesQuery extends QuerySupport implements ProductSalesQuery {

    private String productId;

    private String variantId;

    private Short yearStart;

    private Byte monthStart;

    private Byte dayOfMonthStart;

    private Short yearEnd;

    private Byte monthEnd;

    private Byte dayOfMonthEnd;

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport extends QueryBuilderSupport<ProductSalesQuery, Builder> implements Builder {

        private final DefaultProductSalesQuery query;

        public BuilderSupport(DefaultProductSalesQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder productId(String productId) {
            this.query.setProductId(productId);
            return this;
        }

        @Override
        public Builder variantId(String variantId) {
            this.query.setVariantId(variantId);
            return this;
        }

        @Override
        public Builder yearStart(Short yearStart) {
            this.query.setYearStart(yearStart);
            return this;
        }

        @Override
        public Builder monthStart(Byte monthStart) {
            this.query.setMonthStart(monthStart);
            return this;
        }

        @Override
        public Builder dayOfMonthStart(Byte dayOfMonthStart) {
            this.query.setDayOfMonthStart(dayOfMonthStart);
            return this;
        }

        @Override
        public Builder yearEnd(Short yearEnd) {
            this.query.setYearEnd(yearEnd);
            return this;
        }

        @Override
        public Builder monthEnd(Byte monthEnd) {
            this.query.setMonthEnd(monthEnd);
            return this;
        }

        @Override
        public Builder dayOfMonthEnd(Byte dayOfMonthEnd) {
            this.query.setDayOfMonthEnd(dayOfMonthEnd);
            return this;
        }
    }
}
