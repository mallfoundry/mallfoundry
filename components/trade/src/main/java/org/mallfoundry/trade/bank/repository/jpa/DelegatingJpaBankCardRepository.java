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

package org.mallfoundry.trade.bank.repository.jpa;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.trade.bank.BankCard;
import org.mallfoundry.trade.bank.BankCardQuery;
import org.mallfoundry.trade.bank.BankCardRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaBankCardRepository implements BankCardRepository {

    private final JpaBankCardRepository repository;

    public DelegatingJpaBankCardRepository(JpaBankCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public BankCard create(String id) {
        return new JpaBankCard();
    }

    @Override
    public BankCard save(BankCard bankCard) {
        return this.repository.save(CastUtils.cast(bankCard));
    }

    @Override
    public Optional<BankCard> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public SliceList<BankCard> findAll(BankCardQuery query) {
        return null;
    }

    @Override
    public void delete(BankCard bankCard) {
        this.repository.delete(CastUtils.cast(bankCard));
    }
}
