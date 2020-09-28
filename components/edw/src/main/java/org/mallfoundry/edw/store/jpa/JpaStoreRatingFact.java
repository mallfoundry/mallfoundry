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

package org.mallfoundry.edw.store.jpa;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.edw.store.ImmutableStoreRatingFactKey;
import org.mallfoundry.edw.store.StoreRatingFact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "mf_edw_store_rating_fact")
@IdClass(ImmutableStoreRatingFactKey.class)
public class JpaStoreRatingFact implements StoreRatingFact {

    @Id
    @Column(name = "tenant_key_")
    private String tenantKey;

    @Id
    @Column(name = "store_key_")
    private String storeKey;

    @Id
    @Column(name = "date_key_")
    private int dateKey;

    @Id
    @Column(name = "time_key_")
    private int timeKey;

    @Column(name = "product_review_rating_")
    private int productReviewRating;

    @Column(name = "product_pricing_rating_")
    private int productPricingRating;

    @Column(name = "courier_service_rating_")
    private int courierServiceRating;

    @Column(name = "customer_service_rating_")
    private int customerServiceRating;

    @Column(name = "product_shipping_rating_")
    private int productShippingRating;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaStoreRatingFact)) {
            return false;
        }
        JpaStoreRatingFact that = (JpaStoreRatingFact) object;
        return dateKey == that.dateKey
                && timeKey == that.timeKey
                && Objects.equals(tenantKey, that.tenantKey)
                && Objects.equals(storeKey, that.storeKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantKey, storeKey, dateKey, timeKey);
    }
}
