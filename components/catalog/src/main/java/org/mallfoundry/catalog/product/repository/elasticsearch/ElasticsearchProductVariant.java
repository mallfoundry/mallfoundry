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

package org.mallfoundry.catalog.product.repository.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.catalog.product.ProductVariantSupport;
import org.mallfoundry.inventory.InventoryStatus;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ElasticsearchProductVariant extends ProductVariantSupport {

    private String id;

    private String productId;

    private String storeId;

    private BigDecimal weight;

    private BigDecimal width;

    private BigDecimal height;

    private BigDecimal depth;

    private String barcode;

    private String sku;

    private BigDecimal price;

    private BigDecimal salePrice;

    private BigDecimal retailPrice;

    private BigDecimal costPrice;

    private int inventoryQuantity;

    private InventoryStatus inventoryStatus;

    private List<OptionSelection> optionSelections = new ArrayList<>();

    private List<String> imageUrls;

    private int position;

    public ElasticsearchProductVariant(String id) {
        super(id);
    }

    public static ElasticsearchProductVariant of(ProductVariant variant) {
        if (variant instanceof ElasticsearchProductVariant) {
            return (ElasticsearchProductVariant) variant;
        }
        var target = new ElasticsearchProductVariant();
        BeanUtils.copyProperties(variant, target);
        return target;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
        super.setInventoryQuantity(inventoryQuantity);
    }
}
