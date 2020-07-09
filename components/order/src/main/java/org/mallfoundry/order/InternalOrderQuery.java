/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.order;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.QuerySupport;
import org.mallfoundry.payment.methods.PaymentMethod;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class InternalOrderQuery extends QuerySupport implements OrderQuery {

    private Set<String> ids;

    private String name;

    private Set<OrderStatus> statuses;

    private Set<OrderType> types;

    private Set<PaymentMethod> paymentMethods;

    private Set<OrderSource> sources;

    private String storeId;

    private String customerId;

    private Date minPlacedTime;

    private Date maxPlacedTime;
}
