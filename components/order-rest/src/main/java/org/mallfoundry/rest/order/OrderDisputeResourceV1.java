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

package org.mallfoundry.rest.order;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.aftersales.OrderDispute;
import org.mallfoundry.order.aftersales.OrderDisputeService;
import org.mallfoundry.order.aftersales.OrderDisputeStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class OrderDisputeResourceV1 {

    private final OrderDisputeService orderDisputeService;

    public OrderDisputeResourceV1(OrderDisputeService orderDisputeService) {
        this.orderDisputeService = orderDisputeService;
    }

    @GetMapping("/orders/disputes")
    public SliceList<OrderDispute> getOrderDisputes(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                                    @RequestParam(name = "customer_id", required = false) String customerId,
                                                    @RequestParam(name = "store_id", required = false) String storeId,
                                                    @RequestParam(name = "statuses", required = false) Set<String> statuses,
                                                    @RequestParam(name = "sort", required = false) String sort) {
        return this.orderDisputeService.getOrderDisputes(
                this.orderDisputeService.createOrderDisputeQuery().toBuilder()
                        .page(page).limit(limit).sort(aSort -> aSort.from(sort))
                        .customerId(customerId).storeId(storeId)
                        .statuses(() ->
                                CollectionUtils.emptyIfNull(statuses).stream().map(StringUtils::upperCase)
                                        .map(OrderDisputeStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                        .build());
    }

    @GetMapping("/orders/disputes/count")
    public long countOrderDisputes(@RequestParam(name = "customer_id", required = false) String customerId,
                                   @RequestParam(name = "store_id", required = false) String storeId,
                                   @RequestParam(name = "statuses", required = false) Set<String> statuses) {
        return this.orderDisputeService.countOrderDisputes(
                this.orderDisputeService.createOrderDisputeQuery().toBuilder()
                        .customerId(customerId).storeId(storeId)
                        .statuses(() ->
                                CollectionUtils.emptyIfNull(statuses).stream().map(StringUtils::upperCase)
                                        .map(OrderDisputeStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                        .build());
    }
}
