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

package org.mallfoundry.autoconfigure.payment;

import com.alipay.api.AlipayClient;
import org.mallfoundry.payment.PaymentClient;
import org.mallfoundry.payment.PaymentClientFactory;
import org.mallfoundry.payment.alipay.AliPaymentClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConditionalOnClass(PaymentClientFactory.class)
@Configuration
@EnableConfigurationProperties(PaymentProperties.class)
public class PaymentAutoConfiguration {

    private final PaymentProperties properties;

    public PaymentAutoConfiguration(PaymentProperties properties) {
        this.properties = properties;
    }

    @ConditionalOnClass(AlipayClient.class)
    @ConditionalOnProperty(prefix = "mall.payment.alipay",
            name = {"server-url", "app-id", "alipay-public-key", "app-private-key"})
    @Bean
    public AliPaymentClient aliPaymentClient() {
        return new AliPaymentClient(this.properties.getAlipay());
    }

    @Bean
    public PaymentClientFactory paymentClientFactory(List<PaymentClient> clients) {
        return new PaymentClientFactory(clients);
    }
}

