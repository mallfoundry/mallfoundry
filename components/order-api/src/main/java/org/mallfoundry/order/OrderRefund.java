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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderRefund {

    String getId();

    void setId(String id);

    String getOrderId();

    OrderRefundKind getKind();

    OrderRefundStatus getStatus();

    List<OrderRefundItem> getItems();

    BigDecimal getTotalAmount();

    String getDisapprovedReason();

    String getFailReason();

    Date getAppliedTime();

    Date getApprovedTime();

    Date getDisapprovedTime();

    Date getSucceededTime();

    Date getFailedTime();

    /**
     * 申请退款。
     */
    void apply() throws OrderRefundException;

    /**
     * 同意退款。
     */
    void approve() throws OrderRefundException;

    void disapprove(String disapprovedReason) throws OrderRefundException;

    /**
     * 退款成功。
     */
    void succeed() throws OrderRefundException;

    /**
     * 退款失败。
     */
    void fail(String failReason) throws OrderRefundException;
}
