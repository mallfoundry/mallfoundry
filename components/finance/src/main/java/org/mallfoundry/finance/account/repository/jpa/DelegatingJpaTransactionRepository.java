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

package org.mallfoundry.finance.account.repository.jpa;

import org.mallfoundry.finance.Transaction;
import org.mallfoundry.finance.TransactionRepository;
import org.mallfoundry.finance.repository.jpa.JpaTransaction;
import org.mallfoundry.finance.repository.jpa.JpaTransactionRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaTransactionRepository implements TransactionRepository {

    private final JpaTransactionRepository repository;

    public DelegatingJpaTransactionRepository(JpaTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction create(String id) {
        return new JpaTransaction(id);
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public Transaction save(Transaction transaction) {
        return this.repository.save(CastUtils.cast(transaction));
    }

    @Override
    public void delete(Transaction transaction) {
        this.repository.delete(CastUtils.cast(transaction));
    }
}
