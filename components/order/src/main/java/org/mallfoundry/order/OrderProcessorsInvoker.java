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

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.ProcessorsInvoker;

import java.util.List;
import java.util.function.BiFunction;

public class OrderProcessorsInvoker {

    private final List<OrderProcessor> processors;

    public OrderProcessorsInvoker(List<OrderProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    private List<Order> invokeProcess(List<Order> orders, BiFunction<OrderProcessor, List<Order>, List<Order>> function) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, orders, function);
    }

    private Order invokeProcess(Order order, BiFunction<OrderProcessor, Order, Order> function) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, order, function);
    }

    private SliceList<Order> invokeProcess(SliceList<Order> orders, BiFunction<OrderProcessor, SliceList<Order>, SliceList<Order>> function) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, orders, function);
    }

    private OrderQuery invokeProcess(OrderQuery query, BiFunction<OrderProcessor, OrderQuery, OrderQuery> function) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, query, function);
    }

    public List<Order> invokePreProcessPlaceOrders(List<Order> orders) {
        return this.invokeProcess(orders, OrderProcessor::preProcessPlaceOrders);
    }

    public List<Order> invokePostProcessPlaceOrders(List<Order> orders) {
        return this.invokeProcess(orders, OrderProcessor::postProcessPlaceOrders);
    }

    public Order invokePostProcessGetOrder(Order order) {
        return this.invokeProcess(order, OrderProcessor::postProcessGetOrder);
    }

    public OrderQuery invokePreProcessGetOrders(OrderQuery query) {
        return this.invokeProcess(query, OrderProcessor::preProcessGetOrders);
    }

    public SliceList<Order> invokePostProcessGetOrders(SliceList<Order> orders) {
        return this.invokeProcess(orders, OrderProcessor::postProcessGetOrders);
    }

    public Order invokePreProcessUpdateOrder(Order order) {
        return this.invokeProcess(order, OrderProcessor::preProcessUpdateOrder);
    }

    public Order invokePreProcessFulfilOrder(Order order) {
        return this.invokeProcess(order, OrderProcessor::preProcessFulfilOrder);
    }

    public String invokePreProcessSignOrder(Order order, String message) {
        BiFunction<OrderProcessor, String, String> function = (orderProcessor, identity) -> orderProcessor.preProcessSignOrder(order, identity);
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, message, function);
    }

    public Order invokePreProcessReceiptOrder(Order order) {
        return this.invokeProcess(order, OrderProcessor::preProcessReceiptOrder);
    }

    public String invokePreProcessCancelOrder(Order order, String reason) {
        BiFunction<OrderProcessor, String, String> function = (orderProcessor, identity) -> orderProcessor.preProcessCancelOrder(order, reason);
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, reason, function);
    }

    public Shipment invokePreProcessAddOrderShipment(Order order, Shipment shipment) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, shipment, (orderProcessor, identity) -> orderProcessor.preProcessAddOrderShipment(order, identity));
    }

    public Order invokePreProcessGetOrderShipment(Order order) {
        return this.invokeProcess(order, OrderProcessor::preProcessGetOrderShipment);
    }

    public Order invokePreProcessGetOrderShipments(Order order) {
        return this.invokeProcess(order, OrderProcessor::preProcessGetOrderShipments);
    }

    public Shipment invokePreProcessUpdateOrderShipment(Order order, Shipment shipment) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, shipment, (orderProcessor, identity) -> orderProcessor.preProcessUpdateOrderShipment(order, identity));
    }

    public List<Shipment> invokePreProcessUpdateOrderShipments(Order order, List<Shipment> shipments) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, shipments, (orderProcessor, identity) -> orderProcessor.preProcessUpdateOrderShipments(order, identity));
    }

    public Shipment invokePreProcessRemoveOrderShipment(Order order, Shipment shipment) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, shipment, (orderProcessor, identity) -> orderProcessor.preProcessRemoveOrderShipment(order, identity));
    }

    public List<Shipment> invokePreProcessRemoveOrderShipments(Order order, List<Shipment> shipments) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, shipments, (orderProcessor, identity) -> orderProcessor.preProcessRemoveOrderShipments(order, identity));
    }

    public OrderRefund invokePreProcessApplyOrderRefund(Order order, OrderRefund refund) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, refund, (orderProcessor, identity) -> orderProcessor.preProcessApplyOrderRefund(order, identity));
    }

    public void invokePreProcessCancelOrderRefund(Order order, OrderRefund refund) {
        ProcessorsInvoker.invokeBiConsumerProcessors(this.processors, refund, (orderProcessor, identity) -> orderProcessor.preProcessCancelOrderRefund(order, identity));
    }

    public void invokePreProcessApproveOrderRefund(Order order, OrderRefund refund) {
        ProcessorsInvoker.invokeBiConsumerProcessors(this.processors, refund, (orderProcessor, identity) -> orderProcessor.preProcessApproveOrderRefund(order, identity));
    }

    public String invokePreProcessDisapproveOrderRefund(Order order, OrderRefund refund, String disapprovedReason) {
        BiFunction<OrderProcessor, String, String> function = (orderProcessor, identity) -> orderProcessor.preProcessDisapproveOrderRefund(order, refund, identity);
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, disapprovedReason, function);
    }

    public OrderRefund invokePreProcessActiveOrderRefund(Order order, OrderRefund refund) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, refund, (orderProcessor, identity) -> orderProcessor.preProcessActiveOrderRefund(order, identity));
    }

    public void invokePreProcessSucceedOrderRefund(Order order, OrderRefund refund) {
        ProcessorsInvoker.invokeBiConsumerProcessors(this.processors, refund, (orderProcessor, identity) -> orderProcessor.preProcessSucceedOrderRefund(order, identity));
    }

    public String invokePreProcessFailOrderRefund(Order order, OrderRefund refund, String failReason) {
        BiFunction<OrderProcessor, String, String> function = (orderProcessor, identity) -> orderProcessor.preProcessFailOrderRefund(order, refund, identity);
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, failReason, function);
    }

    public void invokePostProcessAfterCompletion() {
        ProcessorsInvoker.invokeConsumerProcessors(this.processors, OrderProcessor::postProcessAfterCompletion);
    }
}
