/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.edw.catalog.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.edw.catalog.ProductFactKey;
import org.mallfoundry.edw.catalog.ProductFactSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_edw_product_fact")
public class JpaProductFact extends ProductFactSupport {

    @Column(name = "tenant_key_")
    private String tenantKey;

    @Column(name = "store_key_")
    private String storeKey;

    @Column(name = "customer_key_")
    private String customerKey;

    @Column(name = "product_key_")
    private String productKey;

    @Id
    @Column(name = "variant_key_")
    private String variantKey;

    @Column(name = "date_key_")
    private int dateKey;

    @Column(name = "time_key_")
    private int timeKey;

    @Column(name = "rating_")
    private int rating;

    @Column(name = "view_count_")
    private int viewCount;

    @Column(name = "added_count_")
    private int addedCount;

    @Column(name = "placed_count_")
    private int placedCount;

    @Column(name = "paid_count_")
    private int paidCount;

    @Column(name = "completed_count_")
    private int completedCount;

    public JpaProductFact(ProductFactKey key) {
        BeanUtils.copyProperties(key, this);
    }
}
