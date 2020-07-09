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

package org.mallfoundry.inventory;

import org.mallfoundry.util.ObjectBuilder;

public interface InventoryAdjustment {

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getQuantityDelta();

    void setQuantityDelta(int quantityDelta);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<InventoryAdjustment> {

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder quantityDelta(int quantityDelta);
    }

    abstract class BuilderSupport implements Builder {

        protected final InventoryAdjustment adjustment;

        protected BuilderSupport(InventoryAdjustment adjustment) {
            this.adjustment = adjustment;
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
        public InventoryAdjustment build() {
            return this.adjustment;
        }
    }
}
