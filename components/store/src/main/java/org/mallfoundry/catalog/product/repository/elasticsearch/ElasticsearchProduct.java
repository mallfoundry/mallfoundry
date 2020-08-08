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
import lombok.Setter;
import org.mallfoundry.catalog.product.DefaultProductOrigin;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductOrigin;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.ProductSupport;
import org.mallfoundry.catalog.product.ProductType;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.inventory.InventoryStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.util.CastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
@Document(indexName = "mf_catalog_product")
public class ElasticsearchProduct extends ProductSupport {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private String storeId;

    @Field(type = FieldType.Keyword)
    private ProductType type;

    @Field(type = FieldType.Keyword)
    private ProductStatus status;

    @Field(type = FieldType.Text)
    private String name;

    private String description;

    private BigDecimal price;

    @Field(type = FieldType.Keyword)
    private String categoryId;

    @Field(type = FieldType.Keyword)
    private String brandId;

    private Set<String> collections = new HashSet<>();

    private long totalSales;

    private long monthlySales;

    private int inventoryQuantity;

    @Field(type = FieldType.Keyword)
    private InventoryStatus inventoryStatus;

    private List<ElasticsearchProductOption> options = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<ElasticsearchProductAttribute> attributes = new ArrayList<>();

    private List<ElasticsearchProductVariant> variants = new ArrayList<>();

    private List<String> imageUrls = new ArrayList<>();

    private List<String> videoUrls = new ArrayList<>();

    private DefaultProductOrigin origin;

    private boolean freeShipping;

    private BigDecimal fixedShippingCost;

    @Field(type = FieldType.Keyword)
    private String shippingRateId;

    private Date publishedTime;

    private Date createdTime;

    public ElasticsearchProduct(String id) {
        super(id);
    }

    public static ElasticsearchProduct of(Product product) {
        if (product instanceof ElasticsearchProduct) {
            return (ElasticsearchProduct) product;
        }
        var target = new ElasticsearchProduct(product.getId());
        BeanUtils.copyProperties(product, target, "options", "attributes", "variants");
        var options = product.getOptions().stream().map(ElasticsearchProductOption::of).collect(Collectors.toList());
        target.setOptions(CastUtils.cast(options));
        var attributes = product.getAttributes().stream().map(ElasticsearchProductAttribute::of).collect(Collectors.toList());
        target.setAttributes(CastUtils.cast(attributes));
        var variants = product.getVariants().stream().map(ElasticsearchProductVariant::of).collect(Collectors.toList());
        target.setVariants(CastUtils.cast(variants));
        return target;
    }

    public void setFixedShippingCost(BigDecimal fixedShippingCost) {
        super.setFixedShippingCost(fixedShippingCost);
        this.fixedShippingCost = fixedShippingCost;
        this.shippingRateId = null;
    }

    public void setShippingRateId(String shippingRateId) {
        super.setShippingRateId(shippingRateId);
        this.shippingRateId = shippingRateId;
        this.fixedShippingCost = null;
    }

    @Override
    public void setOptions(List<ProductOption> options) {
        this.options = Objects.requireNonNullElseGet(options, (Supplier<List<ProductOption>>) ArrayList::new)
                .stream().map(ElasticsearchProductOption::of).collect(Collectors.toList());
    }

    @Override
    public void setVariants(List<ProductVariant> variants) {
        this.variants = Objects.requireNonNullElseGet(variants, (Supplier<List<ProductVariant>>) ArrayList::new)
                .stream().map(ElasticsearchProductVariant::of).collect(Collectors.toList());
    }

    @Override
    public void setOrigin(ProductOrigin shippingOrigin) {
        this.origin = DefaultProductOrigin.of(shippingOrigin);
    }

    @Override
    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = Objects.requireNonNullElseGet(attributes, (Supplier<List<ProductAttribute>>) ArrayList::new)
                .stream().map(ElasticsearchProductAttribute::of).collect(Collectors.toList());
    }

    @Override
    public ProductVariant createVariant(String id) {
        return new ElasticsearchProductVariant(id);
    }

    @Override
    public ProductOption createOption(String id) {
        return new ElasticsearchProductOption(id);
    }

    @Override
    public ProductAttribute createAttribute() {
        return new ElasticsearchProductAttribute();
    }

    @Override
    public ProductAttribute createAttribute(String name, String value) {
        return new ElasticsearchProductAttribute(name, value);
    }

    @Override
    public ProductAttribute createAttribute(String namespace, String name, String value) {
        return new ElasticsearchProductAttribute(namespace, name, value);
    }

    @Override
    public List<ProductVariant> getVariants() {
        return CastUtils.cast(this.variants);
    }

    @Override
    public List<ProductOption> getOptions() {
        return CastUtils.cast(options);
    }

    @Override
    public List<ProductAttribute> getAttributes() {
        return CastUtils.cast(this.attributes);
    }
}
