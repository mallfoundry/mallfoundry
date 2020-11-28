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

package org.mallfoundry.autoconfigure.order;

import org.mallfoundry.inventory.InventoryService;
import org.mallfoundry.order.DeductingInventoryConnector;
import org.mallfoundry.order.DefaultOrderService;
import org.mallfoundry.order.OrderAuthorizeProcessor;
import org.mallfoundry.order.OrderIdentityProcessor;
import org.mallfoundry.order.OrderProcessor;
import org.mallfoundry.order.OrderRepository;
import org.mallfoundry.order.OrderSaveDisputeProcessor;
import org.mallfoundry.order.OrderService;
import org.mallfoundry.order.OrderSplitter;
import org.mallfoundry.order.OrderValidateProcessor;
import org.mallfoundry.order.aftersales.DefaultOrderDisputeService;
import org.mallfoundry.order.aftersales.OrderDisputeRepository;
import org.mallfoundry.order.aftersales.OrderDisputeService;
import org.mallfoundry.order.aftersales.repository.jpa.DelegatingJpaOrderDisputeRepository;
import org.mallfoundry.order.aftersales.repository.jpa.JpaOrderDisputeRepository;
import org.mallfoundry.order.config.OrderConfigurationIdRetrievalStrategy;
import org.mallfoundry.order.expires.OrderExpiredCancellationProcessor;
import org.mallfoundry.order.expires.OrderExpiredCancellationTask;
import org.mallfoundry.order.expires.OrderExpiredCanceller;
import org.mallfoundry.order.review.DefaultOrderReviewService;
import org.mallfoundry.order.review.OrderReviewAuthorizeProcessor;
import org.mallfoundry.order.review.OrderReviewProcessor;
import org.mallfoundry.order.review.OrderReviewRepository;
import org.mallfoundry.order.review.repository.jpa.DelegatingJpaOrderReviewRepository;
import org.mallfoundry.order.review.repository.jpa.JpaOrderReviewRepository;
import org.mallfoundry.finance.PaymentService;
import org.mallfoundry.product.ProductSaleCollectOnOrderEventListener;
import org.mallfoundry.product.ReviewProductOnOrderReviewedEventListener;
import org.mallfoundry.shipping.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
@Import({
        ReviewProductOnOrderReviewedEventListener.class,
        ProductSaleCollectOnOrderEventListener.class,
})
public class OrderAutoConfiguration {

    @Bean
    public OrderConfigurationIdRetrievalStrategy orderConfigurationIdRetrievalStrategy() {
        return new OrderConfigurationIdRetrievalStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(OrderService.class)
    public DefaultOrderService defaultOrderService(@Autowired(required = false)
                                                   @Lazy List<OrderProcessor> processors,
                                                   OrderSplitter orderSplitter,
                                                   CarrierService carrierService,
                                                   PaymentService paymentService,
                                                   OrderRepository orderRepository) {
        var service = new DefaultOrderService(orderSplitter, carrierService, paymentService, orderRepository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public OrderExpiredCanceller orderExpiredCanceller(OrderService orderService) {
        return new OrderExpiredCanceller(orderService);
    }

    @Bean
    @ConditionalOnProperty(value = "mall.order.expired-cancellation.policy", havingValue = "task")
    public OrderExpiredCancellationTask orderPaymentExpirationTask(OrderProperties properties, OrderExpiredCanceller canceller) {
        var task = new OrderExpiredCancellationTask(canceller);
        task.setFetchSize(properties.getExpiredCancellation().getTask().getFetchSize());
        return task;
    }

    @Bean
    @ConditionalOnProperty(value = "mall.order.expired-cancellation.policy", havingValue = "process")
    public OrderExpiredCancellationProcessor orderExpiredCancellationProcessor(OrderExpiredCanceller canceller) {
        return new OrderExpiredCancellationProcessor(canceller);
    }

    @Bean
    public DeductingInventoryConnector deductingInventoryConnector(InventoryService inventoryService) {
        return new DeductingInventoryConnector(inventoryService);
    }

    @Bean
    public OrderIdentityProcessor orderIdentityProcessor() {
        return new OrderIdentityProcessor();
    }

    @Bean
    public OrderAuthorizeProcessor orderAuthorizeProcessor() {
        return new OrderAuthorizeProcessor();
    }

    @Bean
    public OrderValidateProcessor orderValidateProcessor() {
        return new OrderValidateProcessor();
    }

    @Bean
    public OrderSaveDisputeProcessor orderSaveDisputeProcessor(OrderDisputeService orderDisputeService) {
        return new OrderSaveDisputeProcessor(orderDisputeService);
    }

    @Bean
    public DelegatingJpaOrderDisputeRepository delegatingJpaOrderDisputeRepository(JpaOrderDisputeRepository repository) {
        return new DelegatingJpaOrderDisputeRepository(repository);
    }

    @Bean
    public DefaultOrderDisputeService orderDisputeService(OrderDisputeRepository orderDisputeRepository) {
        return new DefaultOrderDisputeService(orderDisputeRepository);
    }

    @Bean
    public DelegatingJpaOrderReviewRepository delegatingJpaOrderReviewRepository(JpaOrderReviewRepository repository) {
        return new DelegatingJpaOrderReviewRepository(repository);
    }

    @Bean
    public DefaultOrderReviewService defaultOrderReviewService(OrderReviewRepository repository,
                                                               @Lazy List<OrderReviewProcessor> processors) {
        var service = new DefaultOrderReviewService(repository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public OrderReviewAuthorizeProcessor orderReviewAuthorizeProcessor() {
        return new OrderReviewAuthorizeProcessor();
    }
}
