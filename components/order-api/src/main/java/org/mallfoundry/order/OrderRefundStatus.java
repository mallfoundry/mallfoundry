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

public enum OrderRefundStatus {
    INCOMPLETE /* 空状态 */,
    APPLYING /* 买家申请退款 */,
    CANCELLED, /* 取消申请退款 */
    DISAPPROVED /* 退款未批准 */,

    AWAITING_RETURN_SHIPMENT /* 等待退货 */,
    PARTIALLY_RETURN_SHIPPED /* 部分已退货 */,
    RETURN_SHIPPED /* 已退货 */,

    PENDING /* 退款中 */,
    SUCCEEDED  /* 退款成功 */,
    FAILED  /* 退款失败 */;
}
