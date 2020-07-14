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

package org.mallfoundry.catalog.product;

import lombok.NoArgsConstructor;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.inventory.InventoryException;
import org.mallfoundry.inventory.InventoryStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Each product can have multiple variations.
 * The product variant is a combination of option values.
 *
 * @author Zhi Tang
 */
@NoArgsConstructor
public abstract class ProductVariantSupport implements MutableProductVariant {

    public ProductVariantSupport(String id) {
        this.setId(id);
    }

    private void checkInventory(int quantity) {
        this.setInventoryStatus(quantity == 0 ? InventoryStatus.OUT_OF_STOCK : InventoryStatus.IN_STOCK);
    }

    @Override
    public void adjustInventoryQuantity(int quantityDelta) throws InventoryException {
        this.setInventoryQuantity(this.getInventoryQuantity() + quantityDelta);
    }

    @Override
    public void setInventoryQuantity(int quantity) throws InventoryException {
        if (quantity < 0) {
            throw new InventoryException("Inventory quantity cannot be less than zero");
        }
        this.checkInventory(quantity);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this);
    }

    protected static class BuilderSupport implements Builder {

        private final ProductVariant variant;

        protected BuilderSupport(ProductVariant variant) {
            this.variant = variant;
        }

        @Override
        public Builder weight(BigDecimal weight) {
            this.variant.setWeight(weight);
            return this;
        }

        @Override
        public Builder width(BigDecimal width) {
            this.variant.setWidth(width);
            return this;
        }

        @Override
        public Builder height(BigDecimal height) {
            this.variant.setHeight(height);
            return this;
        }

        @Override
        public Builder depth(BigDecimal depth) {
            this.variant.setDepth(depth);
            return this;
        }

        @Override
        public Builder barcode(String barcode) {
            this.variant.setBarcode(barcode);
            return this;
        }

        @Override
        public Builder sku(String sku) {
            this.variant.setSku(sku);
            return this;
        }

        @Override
        public Builder price(BigDecimal price) {
            this.variant.setPrice(price);
            return this;
        }

        @Override
        public Builder salePrice(BigDecimal salePrice) {
            this.variant.setSalePrice(salePrice);
            return this;
        }

        @Override
        public Builder retailPrice(BigDecimal retailPrice) {
            this.variant.setRetailPrice(retailPrice);
            return this;
        }

        @Override
        public Builder costPrice(BigDecimal costPrice) {
            this.variant.setCostPrice(costPrice);
            return this;
        }

        public Builder price(double price) {
            this.variant.setPrice(BigDecimal.valueOf(price));
            return this;
        }

        public Builder retailPrice(double retailPrice) {
            this.variant.setRetailPrice(BigDecimal.valueOf(retailPrice));
            return this;
        }

        @Override
        public Builder inventoryQuantity(int quantity) {
            this.variant.setInventoryQuantity(quantity);
            return this;
        }

        public Builder adjustInventoryQuantity(int quantityDelta) {
            this.variant.adjustInventoryQuantity(quantityDelta);
            return this;
        }

        public Builder position(int position) {
            this.variant.setPosition(position);
            return this;
        }

        public Builder optionSelections(List<OptionSelection> optionSelections) {
            this.variant.setOptionSelections(optionSelections);
            return this;
        }

        public Builder imageUrls(List<String> imageUrls) {
            this.variant.setImageUrls(imageUrls);
            return this;
        }

        public ProductVariant build() {
            return this.variant;
        }
    }

}
