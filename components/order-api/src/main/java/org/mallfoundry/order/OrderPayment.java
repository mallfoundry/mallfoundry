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

package org.mallfoundry.order;

import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentInstrument;
import org.mallfoundry.payment.PaymentMethod;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Set;
import java.util.function.Function;

public interface OrderPayment extends ObjectBuilder.ToBuilder<OrderPayment.Builder> {

    PaymentInstrument createInstrument(PaymentMethod type);

    PaymentInstrument getInstrument();

    void setInstrument(PaymentInstrument instrument);

    Set<String> getOrderIds();

    void setOrderIds(Set<String> orderIds);

    String getReturnUrl();

    void setReturnUrl(String returnUrl);

    Payment toPayment();

    interface Builder extends ObjectBuilder<OrderPayment> {

        Builder orderIds(Set<String> orderIds);

        Builder returnUrl(String returnUrl);

        Builder instrument(PaymentInstrument instrument);

        Builder instrument(InstrumentFunction function);

        Builder instrument(Function<PaymentInstrument, PaymentInstrument> function);

        @FunctionalInterface
        interface InstrumentFunction extends Function<OrderPayment, PaymentInstrument> {

        }
    }
}
