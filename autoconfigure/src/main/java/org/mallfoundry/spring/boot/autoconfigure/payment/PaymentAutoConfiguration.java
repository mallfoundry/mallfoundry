/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.spring.boot.autoconfigure.payment;

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

