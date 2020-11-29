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

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.inventory.InventoryException;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface ProductVariant extends Serializable, ObjectBuilder.ToBuilder<ProductVariant.Builder>, Position {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    String getProductId();

    void setProductId(String productId);

    BigDecimal getWeight();

    void setWeight(BigDecimal weight);

    BigDecimal getWidth();

    void setWidth(BigDecimal width);

    BigDecimal getHeight();

    void setHeight(BigDecimal height);

    BigDecimal getDepth();

    void setDepth(BigDecimal depth);

    String getBarcode();

    void setBarcode(String barcode);

    String getSku();

    void setSku(String sku);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getSalePrice();

    void setSalePrice(BigDecimal salePrice);

    BigDecimal getRetailPrice();

    void setRetailPrice(BigDecimal retailPrice);

    BigDecimal getCostPrice();

    void setCostPrice(BigDecimal costPrice);

    List<OptionSelection> getOptionSelections();

    void setOptionSelections(List<OptionSelection> optionSelections);

    List<String> getImageUrls();

    void setImageUrls(List<String> imageUrls);

    int getInventoryQuantity();

    void setInventoryQuantity(int inventoryQuantity) throws InventoryException;

    void adjustInventoryQuantity(int quantityDelta) throws InventoryException;

    InventoryStatus getInventoryStatus();

    interface Builder extends ObjectBuilder<ProductVariant> {

        Builder weight(BigDecimal weight);

        Builder width(BigDecimal width);

        Builder height(BigDecimal height);

        Builder depth(BigDecimal depth);

        Builder barcode(String barcode);

        Builder sku(String sku);

        Builder price(BigDecimal price);

        Builder price(double price);

        Builder salePrice(BigDecimal salePrice);

        Builder retailPrice(BigDecimal retailPrice);

        Builder retailPrice(double retailPrice);

        Builder costPrice(BigDecimal costPrice);

        Builder inventoryQuantity(int quantity);

        Builder adjustInventoryQuantity(int quantityDelta);

        Builder imageUrls(List<String> imageUrls);

        Builder optionSelections(List<OptionSelection> optionSelections);

        Builder position(int position);
    }
}
