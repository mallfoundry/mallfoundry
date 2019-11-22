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

package com.mallfoundry.store.domain.product;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonPropertyOrder({"id", "shortName", "name", "freeShipping", "shippingMoney",
        "description", "variants", "attributes", "options", "images", "videos"})
public class Product extends ProductInfo {

    private static final long serialVersionUID = 1L;

    private List<ProductAttribute> attributes;

    private List<ProductOption> options;

    private List<ProductVariant> variants;

    /**
     * Contains images of product and product variants.
     */
    private List<ProductImage> images;

    private List<ProductVideo> videos;

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
}
