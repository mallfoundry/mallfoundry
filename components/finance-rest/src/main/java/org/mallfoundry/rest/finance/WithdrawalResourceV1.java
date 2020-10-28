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

import org.mallfoundry.finance.Withdrawal;
import org.mallfoundry.finance.WithdrawalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class WithdrawalResourceV1 {

    private final WithdrawalService withdrawalService;

    public WithdrawalResourceV1(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping("/withdrawals")
    public Withdrawal applyWithdrawal(@RequestBody WithdrawalRequest request) {
        var withdrawal = this.withdrawalService.createWithdrawal(null);
        return this.withdrawalService.applyWithdrawal(request.assignTo(withdrawal));
    }

    @GetMapping("/withdrawals/{withdrawal_id}")
    public Withdrawal disapproveWithdrawal(@PathVariable("withdrawal_id") String withdrawalId) {
        return this.withdrawalService.getWithdrawal(withdrawalId);
    }

    @PatchMapping("/withdrawals/{withdrawal_id}/disapprove")
    public Withdrawal disapproveWithdrawal(@PathVariable("withdrawal_id") String withdrawalId,
                                           @RequestBody WithdrawalDisapproveRequest request) {
        return this.withdrawalService.disapproveWithdrawal(withdrawalId, request.getDisapprovalReason());
    }

    @PatchMapping("/withdrawals/{withdrawal_id}/cancel")
    public Withdrawal cancelWithdrawal(@PathVariable("withdrawal_id") String withdrawalId) {
        return this.withdrawalService.cancelWithdrawal(withdrawalId);
    }

    @PostMapping("/withdrawals/{withdrawal_id}/approve")
    public Withdrawal approveWithdrawal(@PathVariable("withdrawal_id") String withdrawalId) {
        return this.withdrawalService.approveWithdrawal(withdrawalId);
    }
}
