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

package org.mallfoundry.browsing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.browsing.repository.jpa.JpaBrowsingProductId;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_browsing_product")
@IdClass(JpaBrowsingProductId.class)
public class InternalBrowsingProduct implements BrowsingProduct {

    @Id
    @Column(name = "id_")
    private String id;

    @Id
    @Column(name = "browser_id_")
    private String browserId;

    @Column(name = "name_")
    private String name;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "hits_")
    private int hits;

    @Column(name = "browsing_time_")
    private Date browsingTime;

    public InternalBrowsingProduct(String browserId, String productId) {
        this.browserId = browserId;
        this.id = productId;
        this.browsingTime = new Date();
    }

    public static InternalBrowsingProduct of(BrowsingProduct browsingProduct) {
        if (browsingProduct instanceof InternalBrowsingProduct) {
            return (InternalBrowsingProduct) browsingProduct;
        }
        var target = new InternalBrowsingProduct();
        BeanUtils.copyProperties(browsingProduct, target);
        return target;
    }

    @Override
    public int hit() {
        this.browsingTime = new Date();
        return this.hits++;
    }
}