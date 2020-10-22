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

package org.mallfoundry.trade;

import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StandaloneTest;
import org.mallfoundry.trade.account.AccountService;
import org.mallfoundry.trade.account.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@StandaloneTest
public class AccountServiceTests {

    @Autowired
    private AccountService accountService;

    @Test
    public void testCreateAccount() {
        var account = this.accountService.createAccount("1");
        account.setName("test name");
        account.setBusinessType(BusinessType.INDIVIDUAL);
        this.accountService.createAccount(account);
    }

    @Test
    public void testCreditAccountBalance() {
        this.accountService.creditAccountBalance("1", "CNY", SourceType.ALIPAY, BigDecimal.valueOf(10));
        this.accountService.creditAccountBalance("1", "CNY", SourceType.WECHAT_PAY, BigDecimal.valueOf(10));
    }

    @Test
    public void testFreezeAccountBalance() {
        this.accountService.freezeAccountBalance("1", "CNY",  BigDecimal.valueOf(10));
    }
}
