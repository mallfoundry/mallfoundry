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

import org.mallfoundry.order.aftersales.OrderDisputeService;
import org.mallfoundry.order.aftersales.OrderRefund;

import java.util.List;

public class OrderSaveDisputeProcessor implements OrderProcessor {

    private final OrderDisputeService orderDisputeService;

    public OrderSaveDisputeProcessor(OrderDisputeService orderDisputeService) {
        this.orderDisputeService = orderDisputeService;
    }

    @Override
    public OrderRefund postProcessAfterApplyOrderRefund(Order order, OrderRefund refund) {
        this.orderDisputeService.saveOrderDispute(refund);
        return refund;
    }

    @Override
    public List<OrderRefund> postProcessAfterApplyOrderRefunds(Order order, List<OrderRefund> refunds) {
        refunds.forEach(this.orderDisputeService::saveOrderDispute);
        return refunds;
    }

    @Override
    public OrderRefund postProcessAfterCancelOrderRefund(Order order, OrderRefund refund) {
        this.orderDisputeService.saveOrderDispute(refund);
        return refund;
    }

    @Override
    public OrderRefund postProcessAfterApproveOrderRefund(Order order, OrderRefund refund) {
        this.orderDisputeService.saveOrderDispute(refund);
        return refund;
    }

    @Override
    public OrderRefund postProcessAfterDisapproveOrderRefund(Order order, OrderRefund refund) {
        this.orderDisputeService.saveOrderDispute(refund);
        return refund;
    }

    @Override
    public OrderRefund postProcessAfterReapplyOrderRefund(Order order, OrderRefund refund) {
        this.orderDisputeService.saveOrderDispute(refund);
        return refund;
    }

    @Override
    public OrderRefund postProcessAfterActiveOrderRefund(Order order, OrderRefund refund) {
        this.orderDisputeService.saveOrderDispute(refund);
        return refund;
    }
}
