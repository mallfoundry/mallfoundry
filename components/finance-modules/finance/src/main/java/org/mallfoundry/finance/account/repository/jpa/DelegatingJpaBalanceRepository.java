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

import org.mallfoundry.finance.account.Balance;
import org.mallfoundry.finance.account.BalanceId;
import org.mallfoundry.finance.account.BalanceRepository;
import org.mallfoundry.finance.account.ImmutableBalanceId;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;

public class DelegatingJpaBalanceRepository implements BalanceRepository {

    private final JpaBalanceRepository repository;

    public DelegatingJpaBalanceRepository(JpaBalanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Balance create(BalanceId balanceId) {
        return new JpaBalance(balanceId);
    }

    @Override
    public Optional<Balance> findById(BalanceId balanceId) {
        return CastUtils.cast(this.repository.findById(ImmutableBalanceId.of(balanceId)));
    }

    @Override
    public List<Balance> findAllByAccountId(String accountId) {
        return CastUtils.cast(this.repository.findAllByAccountId(accountId));
    }

    @Override
    public Balance save(Balance balance) {
        return this.repository.save(CastUtils.cast(balance));
    }
}
