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
import org.mallfoundry.finance.Recharge;
import org.mallfoundry.finance.RechargeService;
import org.mallfoundry.finance.RechargeStatus;
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
public class RechargeResourceV1 {

    private final RechargeService rechargeService;

    public RechargeResourceV1(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @PostMapping("/recharges")
    public Recharge createRecharge(@RequestBody RechargeRequest request) {
        var recharge = this.rechargeService.createRecharge((String) null);
        return this.rechargeService.createRecharge(request.assignTo(recharge));
    }

    @GetMapping("/recharges/{recharge_id}")
    public Recharge getRecharge(@PathVariable("recharge_id") String rechargeId) {
        return this.rechargeService.getRecharge(rechargeId);
    }

    @GetMapping("/recharges")
    public SliceList<Recharge> getRecharges(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                            @RequestParam("account_id") String accountId,
                                            @RequestParam(name = "statuses", required = false) Set<String> statuses,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                            @RequestParam(name = "created_time_start", required = false) Date createdTimeStart,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                            @RequestParam(name = "created_time_end", required = false) Date createdTimeEnd) {
        var query = this.rechargeService.createRechargeQuery().toBuilder()
                .page(page).limit(limit).accountId(accountId)
                .statuses(() ->
                        CollectionUtils.emptyIfNull(statuses).stream().map(StringUtils::upperCase)
                                .map(RechargeStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .createdTimeStart(createdTimeStart).createdTimeEnd(createdTimeEnd)
                .build();
        return this.rechargeService.getRecharges(query);
    }
}
