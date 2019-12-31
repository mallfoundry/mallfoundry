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

package com.mallfoundry.follow;

import com.mallfoundry.customer.CustomerId;
import com.mallfoundry.store.product.ProductId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "follow_product")
public class FollowProduct {

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Integer id;

    @Embedded
    private CustomerId customerId;

    @Embedded
    private ProductId productId;

    public FollowProduct(CustomerId customerId) {
        this.setCustomerId(customerId);
    }

    public FollowProduct(ProductId productId) {
        this.setProductId(productId);
    }

    public FollowProduct(CustomerId customerId, ProductId productId) {
        this.setCustomerId(customerId);
        this.setProductId(productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowProduct that = (FollowProduct) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, productId);
    }
}
