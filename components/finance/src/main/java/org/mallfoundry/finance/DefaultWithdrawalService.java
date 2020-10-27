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

import org.mallfoundry.finance.account.BalanceService;
import org.mallfoundry.finance.account.BalanceTransaction;
import org.mallfoundry.processor.Processors;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultWithdrawalService implements WithdrawalService, WithdrawalProcessorInvoker {

    private List<WithdrawalProcessor> processors;

    private final BalanceService balanceService;

    private final RecipientService recipientService;

    private final WithdrawalRepository withdrawalRepository;

    public DefaultWithdrawalService(BalanceService balanceService,
                                    RecipientService recipientService,
                                    WithdrawalRepository withdrawalRepository) {
        this.balanceService = balanceService;
        this.recipientService = recipientService;
        this.withdrawalRepository = withdrawalRepository;
    }

    public void setProcessors(List<WithdrawalProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public WithdrawalQuery createWithdrawalQuery() {
        return new DefaultWithdrawalQuery();
    }

    @Override
    public Withdrawal createWithdrawal(String id) {
        return this.withdrawalRepository.create(id);
    }

    @Override
    public Withdrawal getWithdrawal(String withdrawalId) {
        return this.withdrawalRepository.findById(withdrawalId).orElseThrow();
    }

    private List<BalanceTransaction> withdrawBalance(Withdrawal withdrawal) throws WithdrawalException {
        var balanceId = this.balanceService.createBalanceId(withdrawal.getAccountId(), withdrawal.getCurrency());
        return this.balanceService.withdrawBalance(balanceId, withdrawal.getAmount());
    }

    @Transactional
    @Override
    public Withdrawal applyWithdrawal(Withdrawal withdrawal) throws WithdrawalException {
        withdrawal = this.invokePreProcessBeforeApplyWithdrawal(withdrawal);
        // 设置 recipient 对象。
        var recipient = withdrawal.getRecipient();
        recipient = this.recipientService.getRecipient(recipient);
        withdrawal.setRecipient(recipient);
        var balanceTransactions = this.withdrawBalance(withdrawal);
        withdrawal.apply(balanceTransactions);
        withdrawal = this.invokePreProcessAfterApplyWithdrawal(withdrawal);
        return this.withdrawalRepository.save(withdrawal);
    }

    private Withdrawal requiredWithdrawal(String withdrawId) throws WithdrawalException {
        return this.withdrawalRepository.findById(withdrawId)
                .orElseThrow(() -> new WithdrawalException(WithdrawalMessages.notFound()));
    }

    @Transactional
    @Override
    public Withdrawal disapproveWithdrawal(String withdrawalId, String disapprovalReason) throws WithdrawalException {
        var withdrawal = this.requiredWithdrawal(withdrawalId);
        withdrawal = this.invokePreProcessBeforeDisapproveWithdrawal(withdrawal);
        withdrawal.disapprove(disapprovalReason);
        withdrawal = this.invokePreProcessAfterDisapproveWithdrawal(withdrawal);
        return this.withdrawalRepository.save(withdrawal);
    }

    @Transactional
    @Override
    public Withdrawal cancelWithdrawal(String withdrawalId) throws WithdrawalException {
        var withdrawal = this.requiredWithdrawal(withdrawalId);
        withdrawal = this.invokePreProcessBeforeCancelWithdrawal(withdrawal);
        withdrawal.cancel();
        withdrawal = this.invokePreProcessAfterCancelWithdrawal(withdrawal);
        return this.withdrawalRepository.save(withdrawal);
    }

    @Transactional
    @Override
    public Withdrawal approveWithdrawal(String withdrawalId) throws WithdrawalException {
        var withdrawal = this.requiredWithdrawal(withdrawalId);
        withdrawal = this.invokePreProcessBeforeApproveWithdrawal(withdrawal);
        withdrawal.approve();
        withdrawal = this.invokePreProcessAfterApproveWithdrawal(withdrawal);
        return this.withdrawalRepository.save(withdrawal);
    }

    @Transactional
    @Override
    public Withdrawal succeedWithdrawal(String withdrawalId) throws WithdrawalException {
        var withdrawal = this.requiredWithdrawal(withdrawalId);
        withdrawal = this.invokePreProcessBeforeSucceedWithdrawal(withdrawal);
        withdrawal.succeed();
        withdrawal = this.invokePreProcessAfterSucceedWithdrawal(withdrawal);
        return this.withdrawalRepository.save(withdrawal);
    }

    @Transactional
    @Override
    public Withdrawal failWithdrawal(String withdrawalId, String failureReason) throws WithdrawalException {
        var withdrawal = this.requiredWithdrawal(withdrawalId);
        withdrawal = this.invokePreProcessBeforeFailWithdrawal(withdrawal);
        withdrawal.fail(failureReason);
        withdrawal = this.invokePreProcessAfterFailWithdrawal(withdrawal);
        return this.withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessBeforeApplyWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessBeforeApplyWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessAfterApplyWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessAfterApplyWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessBeforeDisapproveWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessBeforeDisapproveWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessAfterDisapproveWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessAfterDisapproveWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessBeforeCancelWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessBeforeCancelWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessAfterCancelWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessAfterCancelWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessBeforeApproveWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessBeforeApproveWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessAfterApproveWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessAfterApproveWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessBeforeSucceedWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessBeforeSucceedWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessAfterSucceedWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessAfterSucceedWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessBeforeFailWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessBeforeFailWithdrawal)
                .apply(withdrawal);
    }

    @Override
    public Withdrawal invokePreProcessAfterFailWithdrawal(Withdrawal withdrawal) {
        return Processors.stream(this.processors)
                .map(WithdrawalProcessor::preProcessAfterFailWithdrawal)
                .apply(withdrawal);
    }
}
