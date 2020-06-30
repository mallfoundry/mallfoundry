/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

    @Override
    public void adjustInventoryQuantity(int quantityDelta) throws InventoryException {
        var quantity = this.getInventoryQuantity() + quantityDelta;
        if (quantity < 0) {
            throw new InventoryException("Inventory quantity cannot be less than zero");
        }
        this.setInventoryQuantity(quantity);
        this.setInventoryStatus();
    }

    private void setInventoryStatus() {
        this.setInventoryStatus(
                this.getInventoryQuantity() == 0
                        ? InventoryStatus.OUT_OF_STOCK : InventoryStatus.IN_STOCK);
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

        public Builder price(double price) {
            this.variant.setPrice(BigDecimal.valueOf(price));
            return this;
        }

        public Builder retailPrice(double retailPrice) {
            this.variant.setRetailPrice(BigDecimal.valueOf(retailPrice));
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
