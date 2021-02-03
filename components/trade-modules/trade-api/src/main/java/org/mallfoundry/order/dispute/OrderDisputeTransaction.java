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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderDisputeTransaction {

    String getId();

    void setId(String id);

    String getOrderId();

    String getDisputeId();

    OrderDisputeKind getKind();

    String getItemId();

    String getProductId();

    String getVariantId();

    String getImageUrl();

    String getName();

    int getQuantity();

    // 订单退款项金额
    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    String getNotes();

    void setNotes(String notes);

    List<String> getAttachments();

    void setAttachments(List<String> attachments);

    String getMessage();

    void setMessage(String message);

    OrderDisputeStatus getStatus();

    String getReason();

    Date getAppliedTime();

    Date getCancelledTime();

    Date getApprovedTime();

    String getDisapprovalReason();

    Date getDisapprovedTime();

    Date getSucceededTime();

    String getFailReason();

    Date getFailedTime();

    Date getCreatedTime();

    void setCreatedTime(Date createdTime);
}
