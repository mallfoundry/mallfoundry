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
import lombok.Setter;
import org.mallfoundry.edw.order.DailyOrderFact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "mf_edw_daily_order_fact")
public class JpaDailyOrderFact implements DailyOrderFact {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "tenant_id_")
    private String tenantId;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "customer_id_")
    private String customerId;

    @Column(name = "placed_date_id_")
    private Integer placedDateId;

    @Column(name = "paid_date_id_")
    private Integer paidDateId;

    @Column(name = "fulfilled_date_id_")
    private Integer fulfilledDateId;

    @Column(name = "shipped_date_id_")
    private Integer shippedDateId;

    @Column(name = "signed_date_id_")
    private Integer signedDateId;

    @Column(name = "received_date_id_")
    private Integer receivedDateId;

    @Column(name = "total_quantity_")
    private int totalQuantity;

    @Column(name = "total_shipping_cost_")
    private BigDecimal totalShippingCost;

    @Column(name = "total_discount_shipping_cost_")
    private BigDecimal totalDiscountShippingCost;

    @Column(name = "total_price_")
    private BigDecimal totalPrice;

    @Column(name = "total_discount_total_price_")
    private BigDecimal totalDiscountTotalPrice;

    @Column(name = "subtotal_amount_")
    private BigDecimal subtotalAmount;

    @Column(name = "total_amount_")
    private BigDecimal totalAmount;
}
