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

package org.mallfoundry.cart;

public abstract class CartAdjustmentSupport implements CartAdjustment {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final CartAdjustmentSupport adjustment;

        protected BuilderSupport(CartAdjustmentSupport adjustment) {
            this.adjustment = adjustment;
        }

        @Override
        public Builder itemId(String itemId) {
            this.adjustment.setItemId(itemId);
            return this;
        }

        @Override
        public Builder productId(String productId) {
            this.adjustment.setProductId(productId);
            return this;
        }

        @Override
        public Builder variantId(String variantId) {
            this.adjustment.setVariantId(variantId);
            return this;
        }

        @Override
        public Builder quantityDelta(int quantityDelta) {
            this.adjustment.setQuantityDelta(quantityDelta);
            return this;
        }

        @Override
        public CartAdjustment build() {
            return this.adjustment;
        }
    }
}
