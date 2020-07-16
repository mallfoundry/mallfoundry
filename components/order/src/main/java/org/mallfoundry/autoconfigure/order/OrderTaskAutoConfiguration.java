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

import org.mallfoundry.order.expires.OrderExpiredCancellationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Objects;

@EnableConfigurationProperties(OrderProperties.class)
@Configuration
public class OrderTaskAutoConfiguration implements SchedulingConfigurer {

    private final OrderProperties properties;

    private OrderExpiredCancellationTask expiredCancellationTask;

    @Autowired(required = false)
    public void setPaymentExpirationTask(OrderExpiredCancellationTask expiredCancellationTask) {
        this.expiredCancellationTask = expiredCancellationTask;
    }

    public OrderTaskAutoConfiguration(OrderProperties properties) {
        this.properties = properties;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.configureExpiredCancellationTask(taskRegistrar);
    }

    private void configureExpiredCancellationTask(ScheduledTaskRegistrar taskRegistrar) {
        if (Objects.nonNull(this.expiredCancellationTask)) {
            var corn = properties.getExpiredCancellation().getTask().getCorn();
            taskRegistrar.addCronTask(this.expiredCancellationTask, corn);
        }
    }
}
