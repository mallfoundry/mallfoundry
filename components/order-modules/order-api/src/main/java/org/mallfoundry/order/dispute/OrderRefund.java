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

package org.mallfoundry.order.dispute;

import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRefund extends OrderDispute, ObjectBuilder.ToBuilder<OrderRefund.Builder> {

    interface Builder extends ObjectBuilder<OrderRefund> {

        Builder kind(OrderDisputeKind kind);

        Builder reason(String reason);

        Builder itemStatus(ItemStatus itemStatus);

        Builder itemNotReceive();

        Builder itemReceive();

        Builder itemId(String itemId);

        Builder name(String name);

        Builder imageUrl(String imageUrl);

        Builder amount(BigDecimal amount);

        Builder disapprovalReason(String disapprovalReason);

        Builder failReason(String failReason);

        Builder notes(String notes);

        Builder attachments(List<String> attachments);
    }
}
