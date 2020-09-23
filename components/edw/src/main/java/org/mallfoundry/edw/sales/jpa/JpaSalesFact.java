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

package org.mallfoundry.edw.sales.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.edw.sales.ImmutableSalesFactKey;
import org.mallfoundry.edw.sales.SalesFactKey;
import org.mallfoundry.edw.sales.SalesFactSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_edw_sales_fact")
@IdClass(ImmutableSalesFactKey.class)
public class JpaSalesFact extends SalesFactSupport {

    @Id
    @Column(name = "tenant_key_")
    private String tenantKey;

    @Id
    @Column(name = "store_key_")
    private String storeKey;
    @Id
    @Column(name = "customer_key_")
    private String customerKey;

    @Id
    @Column(name = "product_key_")
    private String productKey;

    @Id
    @Column(name = "variant_key_")
    private String variantKey;

    @Id
    @Column(name = "date_key_")
    private int dateKey;

    @Id
    @Column(name = "time_key_")
    private int timeKey;

    @Column(name = "order_quantity_")
    private int orderQuantity;

    @Column(name = "sales_quantity_")
    private int salesQuantity;

    @Column(name = "sales_amount_")
    private BigDecimal salesAmount = BigDecimal.ZERO;

    public JpaSalesFact(SalesFactKey factKey) {
        this.tenantKey = factKey.getTenantKey();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaSalesFact)) {
            return false;
        }
        JpaSalesFact that = (JpaSalesFact) object;
        return dateKey == that.dateKey
                && timeKey == that.timeKey
                && Objects.equals(tenantKey, that.tenantKey)
                && Objects.equals(storeKey, that.storeKey)
                && Objects.equals(customerKey, that.customerKey)
                && Objects.equals(productKey, that.productKey)
                && Objects.equals(variantKey, that.variantKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantKey, storeKey, customerKey, productKey, variantKey, dateKey, timeKey);
    }
}
