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

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.order.expires.OrderExpiredCancellationTask;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

// mallfoundry.order.expired-cancellation.task.corn
// mallfoundry.order.expired-cancellation.task.fetch-size=20
// mallfoundry.order.expired-cancellation.policy=task,process
@Getter
@Setter
@ConfigurationProperties("mallfoundry.order")
public class OrderProperties {
    @NestedConfigurationProperty
    private ExpiredCancellation expiredCancellation = new ExpiredCancellation();

    enum ExpiredCancellationPolicy {
        TASK, PROCESS
    }

    @Getter
    @Setter
    static class ExpiredCancellation {
        private ExpiredCancellationPolicy policy = ExpiredCancellationPolicy.TASK;
        private ExpiredCancellationTask task = new ExpiredCancellationTask();
    }

    @Getter
    @Setter
    static class ExpiredCancellationTask {
        private String corn;
        private int fetchSize = OrderExpiredCancellationTask.DEFAULT_FETCH_SIZE;
    }
}
