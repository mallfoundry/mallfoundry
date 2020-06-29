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

package org.mallfoundry.follow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.product.ProductStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_follow_product")
@IdClass(InternalFollowProductId.class)
public class InternalFollowProduct implements FollowProduct {

    @JsonIgnore
    @Id
    @Column(name = "follower_id_")
    private String followerId;

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "store_id_")
    private String storeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private ProductStatus status;

    @Column(name = "follow_time_")
    private Date followTime;

    public InternalFollowProduct(String followerId, String id) {
        this.followerId = followerId;
        this.id = id;
        this.followTime = new Date();
    }
}
