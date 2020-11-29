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

package org.mallfoundry.edw.order.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.edw.order.OrderFactKey;
import org.mallfoundry.edw.order.OrderFactSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_edw_order_fact")
public class JpaOrderFact extends OrderFactSupport {

    @Id
    @Column(name = "key_")
    private String key;

    @Column(name = "tenant_key_")
    private String tenantKey;

    @Column(name = "store_key_")
    private String storeKey;

    @Column(name = "customer_key_")
    private String customerKey;

    @Column(name = "placed_date_key_")
    private Integer placedDateKey;

    @Column(name = "placed_time_key_")
    private Integer placedTimeKey;

    @Column(name = "paid_date_key_")
    private Integer paidDateKey;

    @Column(name = "paid_time_key_")
    private Integer paidTimeKey;

    @Column(name = "total_price_")
    private BigDecimal totalPrice;

    @Column(name = "total_amount_")
    private BigDecimal totalAmount;

    @Column(name = "total_quantity_")
    private int totalQuantity;

    public JpaOrderFact(OrderFactKey factKey) {
        BeanUtils.copyProperties(factKey, this);
    }
}
