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

package com.github.shop.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonPropertyOrder({"id", "shortName", "title", "freeShipping", "shippingMoney",
        "description", "items", "attributes", "specs", "imageGallery", "videoGallery"})
public class Product extends ProductInfo {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private List<ProductAttribute> attributes;

    @Getter
    @Setter
    private List<ProductSpecification> specs;

    @Getter
    @Setter
    private List<ProductItem> items;


    @Getter
    @Setter
    @JsonProperty("image_gallery")
    private List<ProductImage> imageGallery;

    @Getter
    @Setter
    @JsonProperty("video_gallery")
    private List<ProductVideo> videoGallery;
}
