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

package org.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mallfoundry.order.repository.jpa.convert.RefundItemListConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mf_order_refund")
public class InternalRefund implements Refund {

    @Id
    @Column(name = "id_")
    private String id;

    @JsonProperty("order_id")
    @Column(name = "order_id_")
    private String orderId;

    @JsonProperty("total_amount")
    @Column(name = "total_amount_")
    private BigDecimal totalAmount;

    @Convert(converter = RefundItemListConverter.class)
    @Column(name = "items_")
    private List<RefundItem> items;

    @JsonProperty("created_time")
    @Column(name = "created_time_")
    private Date createdTime;

}
