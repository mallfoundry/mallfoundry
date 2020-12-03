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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultTransactionService implements TransactionService, TransactionProcessorInvoker {

    private List<TransactionProcessor> processors;

    private final TransactionRepository transactionRepository;

    public DefaultTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void setProcessors(List<TransactionProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public TransactionQuery createTransactionQuery() {
        return new DefaultTransactionQuery();
    }

    @Override
    public Transaction createTransaction(String id) {
        return this.transactionRepository.create(id);
    }

    @Transactional
    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction = this.invokePreProcessBeforeCreateTransaction(transaction);
        transaction = this.invokePreProcessAfterCreateTransaction(transaction);
        return this.transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public Transaction updateTransaction(Transaction transaction) {
        transaction = this.invokePreProcessBeforeUpdateTransaction(transaction);
        transaction = this.invokePreProcessAfterUpdateTransaction(transaction);
        return this.transactionRepository.save(transaction);
    }

    @Override
    public SliceList<Transaction> getTransactions(TransactionQuery query) {
        return this.transactionRepository.findAll(query);
    }

    @Override
    public Transaction invokePreProcessBeforeCreateTransaction(Transaction transaction) {
        return Processors.stream(this.processors)
                .map(TransactionProcessor::preProcessBeforeCreateTransaction)
                .apply(transaction);
    }

    @Override
    public Transaction invokePreProcessAfterCreateTransaction(Transaction transaction) {
        return Processors.stream(this.processors)
                .map(TransactionProcessor::preProcessAfterCreateTransaction)
                .apply(transaction);
    }

    @Override
    public Transaction invokePreProcessBeforeUpdateTransaction(Transaction transaction) {
        return Processors.stream(this.processors)
                .map(TransactionProcessor::preProcessBeforeUpdateTransaction)
                .apply(transaction);
    }

    @Override
    public Transaction invokePreProcessAfterUpdateTransaction(Transaction transaction) {
        return Processors.stream(this.processors)
                .map(TransactionProcessor::preProcessBeforeUpdateTransaction)
                .apply(transaction);
    }
}
