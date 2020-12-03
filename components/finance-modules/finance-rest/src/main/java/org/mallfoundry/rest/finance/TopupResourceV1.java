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

package org.mallfoundry.rest.finance;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.finance.PaymentMethodType;
import org.mallfoundry.finance.Topup;
import org.mallfoundry.finance.TopupService;
import org.mallfoundry.finance.TopupStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class TopupResourceV1 {

    private final TopupService topupService;

    public TopupResourceV1(TopupService topupService) {
        this.topupService = topupService;
    }

    @PostMapping("/topups")
    public Topup createTopup(@RequestBody TopupRequest request) {
        var recharge = this.topupService.createTopup((String) null);
        return this.topupService.createTopup(request.assignTo(recharge));
    }

    @GetMapping("/topups/{recharge_id}")
    public Topup getTopup(@PathVariable("recharge_id") String rechargeId) {
        return this.topupService.getTopup(rechargeId);
    }

    @GetMapping("/topups")
    public SliceList<Topup> getTopups(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                         @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                         @RequestParam("account_id") String accountId,
                                         @RequestParam(name = "statuses", required = false) Set<String> statuses,
                                         @RequestParam(name = "payment_methods", required = false) Set<String> paymentMethods,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         @RequestParam(name = "created_time_start", required = false) Date createdTimeStart,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         @RequestParam(name = "created_time_end", required = false) Date createdTimeEnd) {
        var query = this.topupService.createTopupQuery().toBuilder()
                .page(page).limit(limit).accountId(accountId)
                .statuses(() ->
                        CollectionUtils.emptyIfNull(statuses).stream().map(StringUtils::upperCase)
                                .map(TopupStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .paymentMethods(() ->
                        CollectionUtils.emptyIfNull(paymentMethods).stream().map(StringUtils::upperCase)
                                .map(PaymentMethodType::valueOf).collect(Collectors.toUnmodifiableSet()))
                .createdTimeStart(createdTimeStart).createdTimeEnd(createdTimeEnd)
                .build();
        return this.topupService.getTopups(query);
    }

    @PostMapping("/topups/{recharge_id}/cancel")
    public Topup cancelTopup(@PathVariable("recharge_id") String rechargeId) {
        return this.topupService.cancelTopup(rechargeId);
    }
}
