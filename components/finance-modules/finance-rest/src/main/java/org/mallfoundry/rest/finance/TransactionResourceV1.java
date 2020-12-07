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
import org.mallfoundry.finance.Transaction;
import org.mallfoundry.finance.TransactionService;
import org.mallfoundry.finance.TransactionStatus;
import org.mallfoundry.finance.TransactionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class TransactionResourceV1 {

    private final TransactionService transactionService;

    public TransactionResourceV1(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public SliceList<Transaction> getTransactions(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                                  @RequestParam("account_id") String accountId,
                                                  @RequestParam(name = "types", required = false) Set<String> types,
                                                  @RequestParam(name = "statuses", required = false) Set<String> statuses) {
        var query = this.transactionService.createTransactionQuery().toBuilder()
                .page(page).limit(limit).accountId(accountId)
                .types(() ->
                        CollectionUtils.emptyIfNull(types).stream().map(StringUtils::upperCase)
                                .map(TransactionType::valueOf).collect(Collectors.toUnmodifiableSet()))
                .statuses(() ->
                        CollectionUtils.emptyIfNull(statuses).stream().map(StringUtils::upperCase)
                                .map(TransactionStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .build();
        return this.transactionService.getTransactions(query);
    }
}
