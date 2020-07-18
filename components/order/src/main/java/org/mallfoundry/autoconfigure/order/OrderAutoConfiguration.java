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
import org.mallfoundry.order.DefaultOrderConfiguration;
import org.mallfoundry.order.DefaultOrderService;
import org.mallfoundry.order.OrderAuthorizer;
import org.mallfoundry.order.OrderConfiguration;
import org.mallfoundry.order.OrderIdentifier;
import org.mallfoundry.order.OrderProcessor;
import org.mallfoundry.order.OrderProcessorsInvoker;
import org.mallfoundry.order.OrderRepository;
import org.mallfoundry.order.OrderService;
import org.mallfoundry.order.OrderSplitter;
import org.mallfoundry.order.SmartOrderValidator;
import org.mallfoundry.order.expires.OrderExpiredCancellationProcessor;
import org.mallfoundry.order.expires.OrderExpiredCancellationTask;
import org.mallfoundry.order.expires.OrderExpiredCanceller;
import org.mallfoundry.shipping.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.SmartValidator;

import java.util.List;

@EnableConfigurationProperties(OrderProperties.class)
@Configuration
public class OrderAutoConfiguration {

    @ConditionalOnMissingBean(OrderConfiguration.class)
    @Bean
    public DefaultOrderConfiguration defaultOrderConfiguration(OrderProperties properties) {
        var config = new DefaultOrderConfiguration();
        config.setPlacingExpires(properties.getPlacingExpires());
        config.setInventoryDeduction(properties.getInventoryDeduction());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(OrderService.class)
    public DefaultOrderService defaultOrderService(OrderConfiguration orderConfiguration,
                                                   OrderProcessorsInvoker processorsInvoker,
                                                   OrderRepository orderRepository,
                                                   OrderSplitter orderSplitter,
                                                   CarrierService carrierService,
                                                   ApplicationEventPublisher eventPublisher) {
        return new DefaultOrderService(orderConfiguration, processorsInvoker, orderRepository, orderSplitter, carrierService, eventPublisher);
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

    @Autowired(required = false)
    @Bean
    public OrderProcessorsInvoker orderProcessorsInvoker(@Lazy List<OrderProcessor> processors) {
        return new OrderProcessorsInvoker(processors);
    }

    @Bean
    public DeductingInventoryConnector deductingInventoryConnector(InventoryService inventoryService) {
        return new DeductingInventoryConnector(inventoryService);
    }

    @Bean
    @ConditionalOnMissingBean
    public OrderIdentifier orderIdentifier() {
        return new OrderIdentifier();
    }

    @Bean
    @ConditionalOnMissingBean
    public OrderAuthorizer orderAuthorizer() {
        return new OrderAuthorizer();
    }

    @Bean
    @ConditionalOnMissingBean
    public SmartOrderValidator smartOrderValidator(SmartValidator validator) {
        return new SmartOrderValidator(validator);
    }
}
