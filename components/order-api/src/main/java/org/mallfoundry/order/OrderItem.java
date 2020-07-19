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

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItem {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    List<OptionSelection> getOptionSelections();

    void setOptionSelections(List<OptionSelection> optionSelections);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    String getName();

    void setName(String name);

    int getQuantity();

    void setQuantity(int quantity);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getShippingCost();

    void setShippingCost(BigDecimal shippingCost);

    BigDecimal getDiscountShippingCost();

    void setDiscountShippingCost(BigDecimal discountShippingCost);

    BigDecimal getTotalPrice();

    BigDecimal getSubtotalAmount();

    BigDecimal getDiscountAmount();

    void setDiscountAmount(BigDecimal discountAmount);

    BigDecimal getRefundedAmount();

    void setRefundedAmount(BigDecimal refundedAmount);

    BigDecimal getTotalAmount();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<OrderItem> {

        Builder storeId(String storeId);

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder optionSelections(List<OptionSelection> optionSelections);

        Builder imageUrl(String imageUrl);

        Builder name(String name);

        Builder quantity(int quantity);

        Builder price(BigDecimal price);
    }

    abstract class BuilderSupport implements Builder {

        private final OrderItem item;

        public BuilderSupport(OrderItem item) {
            this.item = item;
        }

        @Override
        public Builder storeId(String storeId) {
            this.item.setStoreId(storeId);
            return this;
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
        public Builder optionSelections(List<OptionSelection> optionSelections) {
            this.item.setOptionSelections(optionSelections);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.item.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.item.setName(name);
            return this;
        }

        @Override
        public Builder quantity(int quantity) {
            this.item.setQuantity(quantity);
            return this;
        }

        @Override
        public Builder price(BigDecimal price) {
            this.item.setPrice(price);
            return this;
        }

        @Override
        public OrderItem build() {
            return this.item;
        }
    }
}
