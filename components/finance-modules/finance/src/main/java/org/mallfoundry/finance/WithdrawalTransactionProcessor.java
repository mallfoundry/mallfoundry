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

package org.mallfoundry.finance;

public class WithdrawalTransactionProcessor implements WithdrawalProcessor {

    private final TransactionService transactionService;

    public WithdrawalTransactionProcessor(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public Withdrawal preProcessAfterApplyWithdrawal(Withdrawal withdrawal) {
        var transaction = this.transactionService.createTransaction((String) null);
        transaction.setDirection(TransactionDirection.CREDIT);
        transaction.setSourceId(withdrawal.getId());
        transaction.setOperatorId(withdrawal.getOperatorId());
        transaction.setOperator(withdrawal.getOperator());
        transaction.setAccountId(withdrawal.getAccountId());
        transaction.setCurrencyCode(withdrawal.getCurrencyCode());
        transaction.setAmount(withdrawal.getAmount());
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreatedTime(withdrawal.getAppliedTime());
        transaction = this.transactionService.createTransaction(transaction);
//        withdrawal.setTransactionId(transaction.getId());
        return withdrawal;
    }
}
