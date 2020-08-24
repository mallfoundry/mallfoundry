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

package org.mallfoundry.order.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.mallfoundry.order.OrderRefundItem;
import org.mallfoundry.order.OrderRefundStatus;
import org.mallfoundry.order.OrderRefundSupport;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_order_refund")
public class JpaOrderRefund extends OrderRefundSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "tenant_id_")
    private String tenantId;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "customer_id_")
    private String customerId;

    @Column(name = "order_id_")
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind_")
    private RefundKind kind;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status_")
    private ItemStatus itemStatus;

    @OneToMany(targetEntity = JpaOrderRefundItem.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "refund_id_", referencedColumnName = "id_")
    @OrderBy("position")
    private List<OrderRefundItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private OrderRefundStatus status = OrderRefundStatus.INCOMPLETE;

    @Column(name = "total_amount_")
    private BigDecimal totalAmount;

    @Column(name = "reason_")
    private String reason;

    @Column(name = "disapproval_reason_")
    private String disapprovalReason;

    @Column(name = "fail_reason_")
    private String failReason;

    @Column(name = "applied_time_")
    private Date appliedTime;

    @Column(name = "approved_time_")
    private Date approvedTime;

    @Column(name = "succeeded_time_")
    private Date succeededTime;

    @Column(name = "failed_time_")
    private Date failedTime;

    @Column(name = "disapproved_time_")
    private Date disapprovedTime;

    @Column(name = "notes_")
    private String notes;

    @Column(name = "attachments_")
    @Convert(converter = StringListConverter.class)
    private List<String> attachments;

    public JpaOrderRefund(String orderId, String id) {
        this.orderId = orderId;
        this.id = id;
    }

    @Override
    public OrderRefundItem createItem(String itemId) {
        return new JpaOrderRefundItem(itemId);
    }
}
