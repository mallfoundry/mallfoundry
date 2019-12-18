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

package com.mallfoundry.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer_browse_product")
public class BrowsingProduct {

    @Id
    @Column(name = "id_")
    private Integer id;

    @Column(name = "customer_id_")
    private String customerId;

    @Column(name = "product_id_")
    private String productId;

    @Column(name = "browsing_time_")
    private Date browsingTime;

    public BrowsingProduct(String customerId, String productId) {
        this.customerId = customerId;
        this.productId = productId;
        this.setNowBrowsingTime();
    }

    public BrowsingProduct(String customerId) {
        this.customerId = customerId;
    }

    public void setNowBrowsingTime() {
        this.setBrowsingTime(new Date());
    }

    public static List<BrowsingProduct> of(String customerId, List<String> productIds) {
        if (productIds == null) {
            return Collections.emptyList();
        }
        return productIds.stream().map(productId -> new BrowsingProduct(customerId, productId)).collect(Collectors.toList());
    }
}
