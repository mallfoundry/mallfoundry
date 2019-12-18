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
import com.mallfoundry.store.StoreId;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    private Integer id;

    @Embedded
    @JsonProperty("store_id")
    private StoreId storeId;

    @Column(name = "short_name_")
    @JsonProperty("short_name")
    private String shortName;

    @Column(name = "name_")
    private String name;

    @Column(name = "free_shipping_")
    @JsonProperty("free_shipping")
    private boolean freeShipping;

    @Column(name = "shipping_money_")
    @JsonProperty("shipping_money")
    private BigDecimal shippingMoney;

    @Column(name = "description_")
    private String description;

    @ElementCollection
    @CollectionTable(name = "store_product_attribute",
            joinColumns = @JoinColumn(name = "product_id_"))
    private List<ProductAttribute> attributes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id_")
    private List<ProductOption> options = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id_")
    private List<ProductVariant> variants = new ArrayList<>();

    /**
     * Contains images of product and product variants.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = ProductImage.class)
    @JoinColumn(name = "product_id_")
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = ProductVideo.class)
    @JoinColumn(name = "product_id_")
    private List<ProductVideo> videos = new ArrayList<>();

    /**
     * Find the variant's images in the product's images.
     *
     * @param variant variant
     * @return variant's images
     */
    public List<ProductImage> findVariantImages(ProductVariant variant) {
        Collection<String> images = CollectionUtils.emptyIfNull(variant.getImages());
        return this.images.stream().filter(image -> images.contains(image.getId())).collect(Collectors.toList());
    }

    public void addVariant(ProductVariant variant) {
        this.getVariants().add(variant);
    }

    public ProductOption createOption(String name) {
        ProductOption option = new ProductOption(name);
        option.setPosition(this.getOptions().size());
        this.getOptions().add(option);
        return option;
    }

    public void addVideo(ProductVideo video) {
        this.getVideos().add(video);
    }

    public void removeVideo(ProductVideo video) {
        this.getVideos().remove(video);
    }

    public void addImage(ProductImage image) {
        this.getImages().add(image);
    }

    public void addAttribute(ProductAttribute attribute) {
        this.getAttributes().add(attribute);
    }
}
