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

package com.mallfoundry.customer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A buyer's purchase order class.
 */
@Getter
@Setter
public class PurchaseOrder {

    /**
     * Id of purchase order.
     */
    private String id;

    /**
     * Buyer's shopping cart
     */
    private String cart;

    /**
     * Purchased product identification.
     */
    @JsonProperty("product_id")
    private String productId;

    /**
     * Purchased product specifications.
     */
    private List<Integer> specs;

    /**
     * Purchase quantity of purchase order.
     */
    private int quantity;

    private Date createTime;

    public void setCreateTimeIfNull() {
        if (Objects.isNull(this.getCreateTime())) {
            this.setCreateTime(new Date());
        }
    }
}
