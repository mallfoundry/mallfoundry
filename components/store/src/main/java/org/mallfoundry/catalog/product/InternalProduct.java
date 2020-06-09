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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.catalog.DefaultOptionSelection;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.repository.jpa.convert.ProductAttributeListConverter;
import org.mallfoundry.data.jpa.convert.StringListConverter;
import org.mallfoundry.data.jpa.convert.StringSetConverter;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.Positions;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "mf_catalog_product")
public class InternalProduct implements Product {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "store_id_")
    private String storeId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type_")
    private ProductType type;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_")
    private ProductStatus status;

    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @Column(name = "collections_")
    @Convert(converter = StringSetConverter.class)
    private Set<String> collections = new HashSet<>();

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "market_price_")
    private BigDecimal marketPrice;

    @OneToMany(targetEntity = InternalProductOption.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id_")
    @JsonDeserialize(contentAs = InternalProductOption.class)
    private List<ProductOption> options = new ArrayList<>();

    @Column(name = "attributes_", length = 2048)
    @Convert(converter = ProductAttributeListConverter.class)
    @JsonDeserialize(contentAs = InternalProductAttribute.class)
    private List<ProductAttribute> attributes = new ArrayList<>();

    @OneToMany(targetEntity = InternalProductVariant.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonDeserialize(contentAs = InternalProductVariant.class)
    @JoinColumn(name = "product_id_")
    private List<ProductVariant> variants = new ArrayList<>();

    @Column(name = "image_urls_", length = 2048)
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls = new ArrayList<>();

    @Column(name = "video_urls_", length = 2048)
    @Convert(converter = StringListConverter.class)
    private List<String> videoUrls = new ArrayList<>();

    @Column(name = "shipping_origin_")
    private String shippingOrigin;

    @Column(name = "free_shipping_")
    private boolean freeShipping;

    @Column(name = "fixed_shipping_cost_")
    private BigDecimal fixedShippingCost;

    @Column(name = "shipping_rate_id_")
    private String shippingRateId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_time_")
    private Date createdTime;

    public InternalProduct(String id) {
        this.id = id;
    }

    public static InternalProduct of(Product product) {
        if (product instanceof InternalProduct) {
            return (InternalProduct) product;
        }
        var target = new InternalProduct();
        BeanUtils.copyProperties(product, target);
        return target;
    }

    public BigDecimal getPrice() {
        return CollectionUtils.isEmpty(this.getVariants())
                ? this.price
                : this.getVariants().stream()
                        .map(ProductVariant::getPrice)
                        .max(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public int getInventoryQuantity() {
        return CollectionUtils.isEmpty(this.getVariants())
                ? 0
                : this.getVariants()
                        .stream()
                        .mapToInt(ProductVariant::getInventoryQuantity)
                        .sum();
    }

    @Override
    public InventoryStatus getInventoryStatus() {
        return this.getInventoryQuantity() == 0
                ? InventoryStatus.OUT_OF_STOCK
                : InventoryStatus.IN_STOCK;
    }

    @Override
    public void addVariant(ProductVariant variant) {
        this.getVariants().add(variant);
    }

    @Override
    public void adjustVariantInventoryQuantity(String variantId, int quantityDelta) {
        this.getVariant(variantId).orElseThrow().adjustInventoryQuantity(quantityDelta);
    }

    @Override
    public Optional<ProductVariant> getVariant(String id) {
        return this.getVariants()
                .stream()
                .filter(variant -> Objects.equals(variant.getId(), id))
                .findFirst();
    }

    @Override
    public ProductVariant createVariant(String id) {
        var variant = new InternalProductVariant();
        variant.setId(id);
        variant.setProductId(this.getId());
        variant.setStoreId(this.getStoreId());
        return variant;
    }

    @Override
    public ProductOption createOption(String id) {
        return new InternalProductOption(id);
    }

    @Override
    public Optional<ProductOption> getOption(String name) {
        return this.options.stream().filter(option -> Objects.equals(option.getName(), name)).findFirst();
    }

    @Override
    public void addOption(ProductOption option) {
        this.options.add(option);
        Positions.sort(this.options);
    }

    @Override
    public Optional<OptionSelection> selectOption(final String name, final String label) {
        return this.getOption(name)
                .map(option -> Map.entry(option, option.getValue(label).orElseThrow()))
                .map(entry -> new DefaultOptionSelection(entry.getKey().getId(), name, entry.getValue().getId(), label));
    }

    @Override
    public ProductAttribute createAttribute(String name, String value) {
        return new InternalProductAttribute(name, value);
    }

    @Override
    public ProductAttribute createAttribute(String namespace, String name, String value) {
        return new InternalProductAttribute(namespace, name, value);
    }

    @Override
    public Optional<ProductAttribute> getAttribute(String namespace, String name) {
        return Optional.empty();
    }

    @Override
    public void addImageUrl(String imageUrl) {
        this.getImageUrls().add(imageUrl);
    }

    @Override
    public void addVideoUrl(String video) {
        this.getVideoUrls().add(video);
    }

    //    @Override
    public void addAttribute(ProductAttribute attribute) {
        this.getAttributes().add(attribute);
        Positions.sort(this.getAttributes());
    }

    @Override
    public void create() {
        this.setCreatedTime(new Date());
    }
}