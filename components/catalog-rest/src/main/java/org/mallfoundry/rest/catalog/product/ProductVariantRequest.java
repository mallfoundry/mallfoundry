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

package org.mallfoundry.rest.catalog.product;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.rest.catalog.OptionSelectionRequest;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductVariantRequest {
    private String id;
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
    private List<String> imageUrls;
    private List<OptionSelectionRequest> optionSelections;

    public ProductVariant assignTo(ProductVariant variant) {
        return variant.toBuilder()
                .weight(this.weight)
                .width(this.width).height(this.height).depth(this.depth)
                .barcode(this.barcode).sku(this.sku)
                .price(this.price).salePrice(this.salePrice)
                .retailPrice(this.retailPrice).costPrice(this.costPrice)
                .inventoryQuantity(this.inventoryQuantity)
                .imageUrls(this.imageUrls)
                .build();
    }
}
