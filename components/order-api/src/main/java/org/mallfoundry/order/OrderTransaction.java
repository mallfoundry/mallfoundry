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

import org.mallfoundry.util.Position;

import java.math.BigDecimal;

/**
 * 商品订单事务对象，用于记录商品订单的操作日志。比如：支付相关的流程、退款相关的流程等。
 *
 * @author Zhi Tang
 */
public interface OrderTransaction extends Position {

    String getId();

    String getOrderId();

    TransactionEvent getEvent();

    BigDecimal getAmount();

    String getMessage();

    String getOperator();

    String getOperatorId();

    long getTimestamp();

    enum TransactionEvent {
        PURCHASE
    }
}
