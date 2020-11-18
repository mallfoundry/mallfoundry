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

package org.mallfoundry.autoconfigure.thirdpay;

import org.mallfoundry.thirdpay.DefaultPaymentClientFactory;
import org.mallfoundry.thirdpay.PaymentClient;
import org.mallfoundry.thirdpay.PaymentClientFactory;
import org.mallfoundry.thirdpay.alipay.AlipayClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnClass(PaymentClientFactory.class)
@EnableConfigurationProperties(ThirdpayProperties.class)
public class PaymentClientAutoConfiguration {

    private final ThirdpayProperties properties;

    public PaymentClientAutoConfiguration(ThirdpayProperties properties) {
        this.properties = properties;
    }

    @ConditionalOnClass(com.alipay.api.AlipayClient.class)
    @ConditionalOnProperty(prefix = "mall.payment.alipay",
            name = {"server-url", "app-id", "alipay-public-key", "app-private-key"})
    @Bean
    public AlipayClient aliPaymentClient() {
        var alipay = this.properties.getAlipay();
        var client = new AlipayClient();
        client.setAppId(alipay.getAppId());
        client.setAlipayPublicKey(alipay.getAlipayPublicKey());
        client.setAppPrivateKey(alipay.getAppPrivateKey());
        client.setCharset(alipay.getCharset());
        client.setFormat(alipay.getFormat());
        client.setSignType(alipay.getSignType());
        client.setNotifyUrl(alipay.getNotifyUrl());
        client.setServerUrl(alipay.getServerUrl());
        client.setReturnUrl(alipay.getReturnUrl());
        return client;
    }

    @Bean
    public PaymentClientFactory defaultPaymentClientFactory(List<PaymentClient> clients) {
        return new DefaultPaymentClientFactory(clients);
    }
}

