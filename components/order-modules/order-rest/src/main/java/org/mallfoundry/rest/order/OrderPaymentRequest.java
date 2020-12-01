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

package org.mallfoundry.rest.order;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.finance.PaymentInstrument;
import org.mallfoundry.finance.PaymentMethodType;
import org.mallfoundry.order.OrderPayment;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderPaymentRequest {
    private Set<String> orderIds = new HashSet<>();
    private String returnUrl;
    private InstrumentRequest instrument = new InstrumentRequest();

    @Getter
    @Setter
    static class InstrumentRequest {
        private PaymentMethodType type;

        public PaymentInstrument assignTo(PaymentInstrument aInstrument) {
            return aInstrument.toBuilder().type(this.type).build();
        }
    }

    public OrderPayment assignTo(OrderPayment payment) {
        return payment.toBuilder()
                .orderIds(this.orderIds)
                .instrument(this.instrument::assignTo)
                .returnUrl(this.returnUrl)
                .build();
    }
}
