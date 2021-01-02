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
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.option.Option;
import org.mallfoundry.catalog.product.ProductOrigin;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.rest.catalog.option.OptionRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private List<String> categories;
    private String brandId;
    private Set<String> collections;
    private List<String> imageUrls;
    private List<String> videoUrls;
    private Boolean freeShipping;
    private BigDecimal fixedShippingCost;
    private String shippingRateId;
    private ProductOriginRequest origin;
    private List<ProductAttributeRequest> attributes;
    private List<OptionRequest> options;
    private List<ProductVariantRequest> variants;
    private String body;

    private ProductOrigin createOrigin(Product product) {
        return Objects.isNull(this.origin) ? null
                : this.origin.assignTo(product.createOrigin());
    }

    private List<ProductAttribute> createAttributes(Product product) {
        return Objects.isNull(this.attributes) ? null
                : this.attributes.stream()
                        .map(request -> request.assignTo(product.createAttribute()))
                        .collect(Collectors.toList());
    }

    private List<Option> createOptions(Product product) {
        return Objects.isNull(this.options) ? null
                : this.options.stream()
                        .map(request -> request.assignTo(product.createOption(request.getId())))
                        .collect(Collectors.toList());
    }

    private List<OptionSelection> selectOptions(ProductVariantRequest request, Product product) {
        return request.getOptionSelections().stream()
                .map(selection ->
                        product.selectOption(selection.getName(), selection.getValue())
                                .orElseThrow())
                .collect(Collectors.toList());
    }

    private List<ProductVariant> createVariants(Product product) {
        return Objects.isNull(this.variants) ? null
                : this.variants.stream()
                        .map(request -> request.assignTo(product.createVariant(request.getId()))
                                .toBuilder()
                                .optionSelections(this.selectOptions(request, product))
                                .build())
                        .collect(Collectors.toList());
    }

    public Product assignTo(Product product) {
        return product.toBuilder().name(this.name).description(this.description)
                .categories(this.categories).brandId(this.brandId)
                .collections(this.collections)
                .imageUrls(this.imageUrls).videoUrls(this.videoUrls)
                .freeShipping(this.freeShipping)
                .fixedShippingCost(this.fixedShippingCost).shippingRateId(this.shippingRateId)
                .origin(this::createOrigin)
                .attributes(this::createAttributes)
                .options(this::createOptions)
                .variants(this::createVariants)
                .body(this.body)
                .build();
    }

}
