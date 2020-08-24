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

package org.mallfoundry.rest.order;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.order.OrderRefund;
import org.mallfoundry.order.OrderRefund.ItemStatus;
import org.mallfoundry.order.OrderRefund.RefundKind;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderRefundRequest {
    private RefundKind kind;
    private ItemStatus itemStatus;
    private String reason;
    private String notes;
    private List<String> attachments;
    private List<OrderRefundItemRequest> items = new ArrayList<>();

    public OrderRefund assignTo(OrderRefund refund) {
        return refund.toBuilder()
                .kind(this.kind).itemStatus(this.itemStatus)
                .reason(this.reason).notes(this.notes)
                .attachments(this.attachments)
                .items(() -> this.items.stream().map(item -> item.assignTo(refund.createItem(null))).collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Setter
    static class Disapprove {
        private String disapprovedReason;
    }
}
