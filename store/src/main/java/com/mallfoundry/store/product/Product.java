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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mallfoundry.data.jpa.convert.StringListConverter;
import com.mallfoundry.store.product.repository.jpa.convert.ProductAttributeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@JsonPropertyOrder({"id", "shortName", "name", "freeShipping", "shippingMoney",
        "description", "variants", "attributes", "options", "images", "videos"})
@Entity
@Table(name = "store_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Long id;

    @Column(name = "store_id_")
    @JsonProperty("store_id")
    private String storeId;

    @Column(name = "title_")
    private String title;

    @Column(name = "description_")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id_")
    private List<ProductOption> options = new ArrayList<>();

    @Column(name = "attributes_")
    @Convert(converter = ProductAttributeConverter.class)
    private List<ProductAttribute> attributes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id_")
    private List<ProductVariant> variants = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "images_")
    private List<String> images = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "videos_")
    private List<String> videos = new ArrayList<>();

    public void addVariant(ProductVariant variant) {
        this.getVariants().add(variant);
    }

    public Optional<ProductVariant> getVariant(Long id) {
        return this.getVariants()
                .stream()
                .filter(variant -> Objects.equals(variant.getId(), id))
                .findFirst();
    }

    public ProductOption createOption(String name) {
        ProductOption option = new ProductOption(name);
        option.setPosition(this.getOptions().size());
        this.getOptions().add(option);
        return option;
    }

    public void addImage(String image) {
        this.getImages().add(image);
    }

    public void addVideo(String video) {
        this.getVideos().add(video);
    }

    public void addAttribute(ProductAttribute attribute) {
        this.getAttributes().add(attribute);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Product product;

        public Builder() {
            this.product = new Product();
        }

        public Builder storeId(String storeId) {
            this.product.setStoreId(storeId);
            return this;
        }

        public Builder title(String title) {
            this.product.setTitle(title);
            return this;
        }

        public Builder addImage(String image) {
            this.product.addImage(image);
            return this;
        }

        public Builder addVideo(String video) {
            this.product.addVideo(video);
            return this;
        }

        public Product build() {
            return this.product;
        }

    }
}
