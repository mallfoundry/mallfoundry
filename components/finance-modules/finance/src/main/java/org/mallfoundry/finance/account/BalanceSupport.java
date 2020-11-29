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

package org.mallfoundry.finance.account;

import org.mallfoundry.finance.TransactionDirection;
import org.mallfoundry.finance.TransactionType;
import org.mallfoundry.util.DecimalUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BalanceSupport implements MutableBalance {

    @Override
    public BalanceId toId() {
        return new ImmutableBalanceId(this.getAccountId(), this.getCurrencyCode());
    }

    @Override
    public BalanceSource getSource(BalanceSourceType sourceType) {
        return this.getSources().stream()
                .filter(source -> source.getType().equals(sourceType))
                .findFirst()
                .orElse(null);
    }

    private void creditSource(BalanceSourceType sourceType, BigDecimal amount) {
        var source = this.getSource(sourceType);
        if (Objects.isNull(source)) {
            source = this.createSource(sourceType);
            this.getSources().add(source);
        }
        source.credit(amount);
    }

    private void debitSource(BalanceSourceType sourceType, BigDecimal amount) {
        var source = this.getSource(sourceType);
        if (Objects.isNull(source)) {
            throw new BalanceException(String.format("The source(%s) is not null", sourceType));
        }
        source.debit(amount);
    }

    @Override
    public void pending(BalanceSourceType sourceType, BigDecimal amount) throws BalanceException {
        this.creditSource(sourceType, amount);
        this.setPendingAmount(this.getPendingAmount().add(amount));
    }

    @Override
    public void refund(BalanceSourceType sourceType, BigDecimal amount) throws BalanceException {
        this.debitSource(sourceType, amount);
        this.setPendingAmount(this.getPendingAmount().subtract(amount));
    }

    @Override
    public void settle(BigDecimal amount) throws BalanceException {
        this.setPendingAmount(this.getPendingAmount().subtract(amount));
        this.setAvailableAmount(this.getAvailableAmount().add(amount));
    }

    @Override
    public BalanceTransaction recharge(BalanceSourceType sourceType, BigDecimal amount) {
        this.creditSource(sourceType, amount);
        this.setAvailableAmount(this.getAvailableAmount().add(amount));
        var rechargeSource = this.createSource(sourceType);
        rechargeSource.credit(amount);
        return this.rechargeTransaction(amount, List.of(rechargeSource));
    }

    private BalanceTransaction rechargeTransaction(BigDecimal amount, List<BalanceSource> sources) {
        var transaction = this.createTransaction();
        transaction.setAccountId(this.getAccountId());
        transaction.setCurrencyCode(this.getCurrencyCode());
        transaction.setDirection(TransactionDirection.CREDIT);
        transaction.setType(TransactionType.RECHARGE);
        transaction.setAmount(amount);
        transaction.setSources(sources);
        transaction.setEndingBalance(this.getAvailableAmount());
        transaction.create();
        return transaction;
    }

    @Override
    public void debit(BalanceSourceType sourceType, BigDecimal amount) {
        this.debitSource(sourceType, amount);
        this.setAvailableAmount(this.getAvailableAmount().subtract(amount));
    }

    private List<BalanceSource> getWithdrawalSources() {
        return this.getSources().stream()
                .sorted(Comparator.comparing(BalanceSource::getAmount, Comparator.reverseOrder()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public BalanceTransaction withdraw(BigDecimal amount) {
        var debitingAmount = amount;
        var debitingSources = new ArrayList<BalanceSource>();
        for (var source : this.getWithdrawalSources()) {
            var debitedSource = this.createSource(source.getType());
            // debitingAmount <= sourceAmount, 扣减一次。
            // debitingAmount > sourceAmount, 扣减 sourceAmount。
            if (DecimalUtils.lessThanOrEqualTo(debitingAmount, source.getAmount())) {
                source.debit(debitingAmount);
                debitedSource.credit(debitingAmount);
                debitingAmount = BigDecimal.ZERO;
            } else {
                var debitedAmount = source.getAmount();
                source.debit(debitedAmount);
                debitedSource.credit(debitedAmount);
                debitingAmount = debitingAmount.subtract(debitedAmount);
            }
            debitingSources.add(debitedSource);
            if (DecimalUtils.equals(debitingAmount, BigDecimal.ZERO)) {
                break;
            }
        }
        this.setAvailableAmount(this.getAvailableAmount().subtract(amount));
        return this.withdrawTransaction(amount, debitingSources);
    }

    private BalanceTransaction withdrawTransaction(BigDecimal amount, List<BalanceSource> debitingSources) {
        var transaction = this.createTransaction();
        transaction.setAccountId(this.getAccountId());
        transaction.setCurrencyCode(this.getCurrencyCode());
        transaction.setDirection(TransactionDirection.DEBIT);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setSources(debitingSources);
        transaction.setEndingBalance(this.getAvailableAmount());
        transaction.create();
        return transaction;
    }

    @Override
    public void freeze(BigDecimal amount) {
        this.setFreezeAmount(this.getFreezeAmount().add(amount));
        this.setAvailableAmount(this.getAvailableAmount().subtract(amount));
    }

    @Override
    public void unfreeze(BigDecimal amount) {
        this.setFreezeAmount(this.getFreezeAmount().subtract(amount));
        this.setAvailableAmount(this.getAvailableAmount().add(amount));
    }
}
