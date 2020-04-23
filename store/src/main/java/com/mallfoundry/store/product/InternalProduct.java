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

package com.mallfoundry.store.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mallfoundry.data.jpa.convert.LongListConverter;
import com.mallfoundry.data.jpa.convert.StringListConverter;
import com.mallfoundry.store.product.repository.jpa.convert.ProductAttributeListConverter;
import com.mallfoundry.store.product.repository.jpa.convert.ProductOptionListConverter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "store_product")
public class InternalProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Long id;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "title_")
    private String title;

    @Column(name = "description_")
    private String description;

    @Column(name = "collection_ids_")
    @Convert(converter = LongListConverter.class)
    private List<Long> collectionIds = new ArrayList<>();

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "market_price_")
    private BigDecimal marketPrice;

    @Lob
    @Column(name = "options_")
    @Convert(converter = ProductOptionListConverter.class)
    private List<ProductOption> options = new ArrayList<>();

    @Lob
    @Column(name = "attributes_")
    @Convert(converter = ProductAttributeListConverter.class)
    private List<ProductAttribute> attributes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id_")
    private List<ProductVariant> variants = new ArrayList<>();

    @Lob
    @Column(name = "image_urls_")
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls = new ArrayList<>();

    @Lob
    @Column(name = "video_urls_")
    @Convert(converter = StringListConverter.class)
    private List<String> videoUrls = new ArrayList<>();

    @Column(name = "fixed_shipping_cost_")
    private String fixedShippingCost;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_time_")
    private Date createdTime;

    public BigDecimal getPrice() {
        return CollectionUtils.isEmpty(this.getVariants()) ? this.price :
                this.getVariants().stream()
                        .map(ProductVariant::getPrice)
                        .max(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Integer getInventoryQuantity() {
        return CollectionUtils.isEmpty(this.getVariants()) ? 0 :
                this.getVariants()
                        .stream()
                        .mapToInt(ProductVariant::getInventoryQuantity)
                        .sum();
    }

    public void addVariant(ProductVariant variant) {
        this.getVariants().add(variant);
    }

    public Optional<ProductVariant> getVariant(Long id) {
        return this.getVariants()
                .stream()
                .filter(variant -> Objects.equals(variant.getId(), id))
                .findFirst();
    }

    public Optional<ProductVariant> getVariant(String id) {
        return this.getVariant(Long.parseLong(id));
    }

    public ProductOption createOption(String name) {
        ProductOption option = new ProductOption(name);
        option.setPosition(this.getOptions().size());
        this.getOptions().add(option);
        return option;
    }

    public void addImageUrl(String imageUrl) {
        this.getImageUrls().add(imageUrl);
    }

    public void addVideoUrl(String video) {
        this.getVideoUrls().add(video);
    }

    public void addAttribute(ProductAttribute attribute) {
        this.getAttributes().add(attribute);
    }

    public void create() {
        this.setCreatedTime(new Date());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final InternalProduct product;

        public Builder() {
            this.product = new InternalProduct();
        }

        public Builder storeId(String storeId) {
            this.product.setStoreId(storeId);
            return this;
        }

        public Builder title(String title) {
            this.product.setTitle(title);
            return this;
        }

        public Builder addImageUrl(String image) {
            this.product.addImageUrl(image);
            return this;
        }

        public Builder addVideoUrl(String video) {
            this.product.addVideoUrl(video);
            return this;
        }

        public InternalProduct build() {
            return this.product;
        }

    }
}
