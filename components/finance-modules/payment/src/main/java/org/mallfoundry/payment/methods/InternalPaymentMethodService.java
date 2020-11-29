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

package org.mallfoundry.payment.methods;

import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternalPaymentMethodService implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public InternalPaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public PaymentMethodDescription createPaymentMethod(String code) {
        return new InternalPaymentMethod(code);
    }

    @Override
    public PaymentMethodQuery createPaymentMethodQuery() {
        return new InternalPaymentMethodQuery();
    }

    @Override
    public PaymentMethodDescription addPaymentMethod(PaymentMethodDescription method) {
        return this.paymentMethodRepository.save(InternalPaymentMethod.of(method));
    }

    @Override
    public PaymentMethodDescription updatePaymentMethod(PaymentMethodDescription method) {
        return this.paymentMethodRepository.save(InternalPaymentMethod.of(method));
    }

    @Override
    public List<PaymentMethodDescription> getPaymentMethods(PaymentMethodQuery query) {
        return CastUtils.cast(this.paymentMethodRepository.findAll(query));
    }

    @Override
    public Optional<PaymentMethodDescription> getPaymentMethod(String code) {
        return CastUtils.cast(this.paymentMethodRepository.findById(code));
    }

    @Override
    public void deletePaymentMethod(String code) {
        var method = this.paymentMethodRepository.findById(code).orElseThrow();
        this.paymentMethodRepository.delete(method);
    }
}
