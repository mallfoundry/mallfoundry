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

package org.mallfoundry.checkout.repository.jpa;

import org.mallfoundry.checkout.Checkout;
import org.mallfoundry.checkout.CheckoutRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaCheckoutRepository implements CheckoutRepository {

    private final JpaCheckoutRepository repository;

    public DelegatingJpaCheckoutRepository(JpaCheckoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Checkout> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public Checkout save(Checkout checkout) {
        return this.repository.save(JpaCheckout.of(checkout));
    }

    @Override
    public void delete(Checkout checkout) {
        this.repository.delete(JpaCheckout.of(checkout));
    }
}
