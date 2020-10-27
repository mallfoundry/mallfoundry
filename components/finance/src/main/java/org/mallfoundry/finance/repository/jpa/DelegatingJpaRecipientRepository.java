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

package org.mallfoundry.finance.repository.jpa;

import org.mallfoundry.finance.Recipient;
import org.mallfoundry.finance.RecipientRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaRecipientRepository implements RecipientRepository {

    private final JpaRecipientRepository repository;

    public DelegatingJpaRecipientRepository(JpaRecipientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipient create(String id) {
        return new JpaRecipient(id);
    }

    @Override
    public Optional<Recipient> find(Recipient recipient) {
        return CastUtils.cast(this.repository.findOne(Example.of(CastUtils.cast(recipient))));
    }

    @Override
    public Recipient save(Recipient recipient) {
        return this.repository.save(CastUtils.cast(recipient));
    }
}
