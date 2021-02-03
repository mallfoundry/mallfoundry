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

package org.mallfoundry.order.dispute.repository.jpa;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.mallfoundry.order.dispute.OrderDispute;
import org.mallfoundry.order.dispute.OrderDisputeKind;
import org.mallfoundry.order.dispute.OrderDisputeStatus;
import org.mallfoundry.order.dispute.OrderDisputeTransaction;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "mf_order_dispute_transaction")
public class JpaOrderDisputeTransaction implements OrderDisputeTransaction {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "dispute_id_")
    private String disputeId;

    @Column(name = "order_id_")
    private String orderId;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "customer_id_")
    private String customerId;

    @Column(name = "tenant_id_")
    private String tenantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind_")
    private OrderDisputeKind kind;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status_")
    private OrderDispute.ItemStatus itemStatus;

    @Column(name = "item_id_")
    private String itemId;

    @Column(name = "product_id_")
    private String productId;

    @Column(name = "variant_id_")
    private String variantId;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "name_")
    private String name;

    @Column(name = "quantity_")
    private int quantity;

    @Column(name = "amount_")
    private BigDecimal amount;

    @Column(name = "notes_")
    private String notes;

    @Column(name = "attachments_")
    @Convert(converter = StringListConverter.class)
    private List<String> attachments;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private OrderDisputeStatus status = OrderDisputeStatus.INCOMPLETE;

    @Column(name = "message_")
    private String message;

    @Column(name = "reason_")
    private String reason;

    @Column(name = "cancelled_time_")
    private Date cancelledTime;

    @Column(name = "applied_time_")
    private Date appliedTime;

    @Column(name = "disapproval_reason_")
    private String disapprovalReason;

    @Column(name = "disapproved_time_")
    private Date disapprovedTime;

    @Column(name = "approved_time_")
    private Date approvedTime;

    @Column(name = "succeeded_time_")
    private Date succeededTime;

    @Column(name = "fail_reason_")
    private String failReason;

    @Column(name = "failed_time_")
    private Date failedTime;

    @Column(name = "created_time_")
    private Date createdTime;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaOrderDisputeTransaction)) {
            return false;
        }
        JpaOrderDisputeTransaction that = (JpaOrderDisputeTransaction) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
