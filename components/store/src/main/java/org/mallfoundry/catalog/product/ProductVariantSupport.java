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
import org.mallfoundry.inventory.InventoryException;
import org.mallfoundry.inventory.InventoryStatus;

import java.util.Objects;

/**
 * Each product can have multiple variations.
 * The product variant is a combination of option values.
 *
 * @author Zhi Tang
 */
@NoArgsConstructor
public abstract class ProductVariantSupport implements ProductVariant {

    public ProductVariantSupport(String id) {
        this.setId(id);
    }

    protected abstract void doSetInventoryQuantity(int quantity) throws InventoryException;

    @Override
    public void setInventoryQuantity(int quantity) throws InventoryException {
        if (quantity < 0) {
            throw new InventoryException("Inventory quantity cannot be less than zero");
        }
        this.doSetInventoryQuantity(quantity);
    }

    @Override
    public void adjustInventoryQuantity(int quantityDelta) throws InventoryException {
        this.setInventoryQuantity(this.getInventoryQuantity() + quantityDelta);
    }

    @Override
    public InventoryStatus getInventoryStatus() {
        return this.getInventoryQuantity() == 0
                ? InventoryStatus.OUT_OF_STOCK
                : InventoryStatus.IN_STOCK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductVariantSupport that = (ProductVariantSupport) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
