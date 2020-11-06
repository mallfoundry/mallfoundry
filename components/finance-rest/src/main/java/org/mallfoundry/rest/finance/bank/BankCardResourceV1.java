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

package org.mallfoundry.rest.finance.bank;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.finance.bank.BankCard;
import org.mallfoundry.finance.bank.BankCardService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class BankCardResourceV1 {

    private final BankCardService bankCardService;

    public BankCardResourceV1(BankCardService bankCardService) {
        this.bankCardService = bankCardService;
    }

    @PostMapping("/bank-cards")
    public BankCard bindBankCard(@RequestBody BankCardRequest request) {
        var bankCard = this.bankCardService.createBankCard(null);
        return this.bankCardService.bindBankCard(request.assignTo(bankCard));
    }

    @GetMapping("/bank-cards")
    public SliceList<BankCard> getBankCards(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                            @RequestParam(name = "account_id", required = false) String accountId) {
        var query = this.bankCardService.createBankCardQuery()
                .toBuilder().page(page).limit(limit).accountId(accountId).build();
        return this.bankCardService.getBankCards(query);
    }

    @PatchMapping("/bank-cards/{id}")
    public BankCard updateBankCard(@PathVariable("id") String id,
                                   @RequestBody BankCardRequest request) {
        var bankCard = this.bankCardService.createBankCard(id);
        return this.bankCardService.updateBankCard(request.assignTo(bankCard));
    }

    @DeleteMapping("/bank-cards/{card_id}")
    public void unbindBankCard(@PathVariable("card_id") String cardId) {
        this.bankCardService.unbindBankCard(cardId);
    }
}
