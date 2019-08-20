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

package com.github.shop.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ProductCategory {

    public static final byte TOP_LEVEL = 1;

    public static final byte SECOND_LEVEL = 2;

    public static final byte THREE_LEVEL = 3;

    public ProductCategory() {
        this.setCreateTime(new Date());
    }

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @JsonProperty("parent_id")
    private int parentId;

    @Getter
    @Setter
    @JsonProperty("first_letter")
    private String firstLetter;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private byte level;

    @Getter
    @Setter
    @JsonProperty("sort_order")
    private short sortOrder;

    @Getter
    @Setter
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
