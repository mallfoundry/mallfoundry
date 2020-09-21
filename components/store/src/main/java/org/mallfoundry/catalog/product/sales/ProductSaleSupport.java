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

import java.math.BigDecimal;
import java.util.Date;

public abstract class ProductSaleSupport implements ProductSale {

    @Override
    public void adjustTotalAmounts(BigDecimal deltaAmounts) {
        this.setTotalAmounts(this.getTotalAmounts().add(deltaAmounts));
    }

    @Override
    public void adjustTotalQuantities(long quantities) {
        this.setTotalQuantities(this.getTotalQuantities() + quantities);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ProductSaleSupport productSale;

        protected BuilderSupport(ProductSaleSupport productSale) {
            this.productSale = productSale;
        }

        @Override
        public Builder productId(String productId) {
            this.productSale.setProductId(productId);
            return this;
        }

        @Override
        public Builder variantId(String variantId) {
            this.productSale.setVariantId(variantId);
            return this;
        }

        @Override
        public Builder soldDate(Date soldDate) {
            this.productSale.setSoldDate(soldDate);
            return this;
        }

        @Override
        public Builder totalAmounts(BigDecimal totalAmounts) {
            this.productSale.setTotalAmounts(totalAmounts);
            return this;
        }

        @Override
        public Builder totalQuantities(long totalQuantities) {
            this.productSale.setTotalQuantities(totalQuantities);
            return this;
        }

        @Override
        public ProductSale build() {
            return this.productSale;
        }
    }
}
