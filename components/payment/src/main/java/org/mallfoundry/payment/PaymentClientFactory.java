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

package org.mallfoundry.payment;

import org.mallfoundry.payment.alipay.AliPaymentClient;
import org.mallfoundry.util.Position;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentClientFactory {

    private final List<PaymentClient> clients;

    public PaymentClientFactory(List<PaymentClient> clients) {
        this.clients = Collections.unmodifiableList(clients);
    }

    public Optional<PaymentClient> getClient(Payment payment) throws PaymentException {
        return this.clients.stream().filter(client -> client.supportsPayment(payment)).findFirst();
    }

    public List<PaymentProvider> getPaymentProviders() {
        return this.clients.stream()
                .map(this::createPaymentProvider)
                .filter(Objects::nonNull)
                .sorted(Position::compareTo)
                .collect(Collectors.toList());
    }


    public PaymentProvider createPaymentProvider(PaymentClient client) {

        if (client instanceof AliPaymentClient) {
            return new PaymentProvider(PaymentProviderType.ALIPAY, "支付宝", client.getOrder());
        }

        return null;
    }
}
