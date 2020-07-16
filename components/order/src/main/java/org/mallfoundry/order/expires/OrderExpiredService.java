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

package org.mallfoundry.order.expires;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.i18n.MessageHolder;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderExpiredService {

    private static final Logger logger = LoggerFactory.getLogger(OrderExpiredService.class);

    private final OrderService orderService;

    public OrderExpiredService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public void cancelOrders(List<Order> orders) {
        if (CollectionUtils.isNotEmpty(orders)) {
            var reason = this.getCancelReason();
            orders.forEach(order -> {
                this.orderService.cancelOrder(order.getId(), reason);
                if (logger.isDebugEnabled()) {
                    logger.debug("Automatically cancels the expired order({})", order.getId());
                }
            });
        }
    }

    private String getCancelReason() {
        return MessageHolder.message("order.expires.OrderExpiredService.cancelReason",
                "Order expired, the system automatically cancel");
    }
}
