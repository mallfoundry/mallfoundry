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

package org.mallfoundry.order;

import org.mallfoundry.util.ObjectBuilder;

public interface ShipmentItem {

    String getId();

    void setId(String id);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getQuantity();

    void setQuantity(int quantity);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<ShipmentItem> {

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder quantity(int quantity);

        Builder name(String name);

        Builder imageUrl(String imageUrl);
    }

    abstract class BuilderSupport implements Builder {

        protected final ShipmentItem item;

        public BuilderSupport(ShipmentItem item) {
            this.item = item;
        }

        @Override
        public Builder productId(String productId) {
            this.item.setProductId(productId);
            return this;
        }

        @Override
        public Builder variantId(String variantId) {
            this.item.setVariantId(variantId);
            return this;
        }

        @Override
        public Builder quantity(int quantity) {
            this.item.setQuantity(quantity);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.item.setName(name);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.item.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public ShipmentItem build() {
            return this.item;
        }
    }
}
