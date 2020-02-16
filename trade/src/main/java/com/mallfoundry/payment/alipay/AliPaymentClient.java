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

package com.mallfoundry.payment.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.mallfoundry.payment.PaymentClient;
import com.mallfoundry.payment.PaymentConfirmation;
import com.mallfoundry.payment.PaymentException;
import com.mallfoundry.payment.PaymentOrder;
import com.mallfoundry.payment.PaymentProvider;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

import java.util.Map;

public class AliPaymentClient implements PaymentClient {

    private final AliPaymentProperties properties;

    private final AlipayClient alipayClient;

    private final UriTemplateHandler uriTemplateHandler;

    public AliPaymentClient(AliPaymentProperties properties) {
        this.properties = properties;
        this.alipayClient =
                new DefaultAlipayClient(properties.getServerUrl(),
                        properties.getAppId(),
                        properties.getAppPrivateKey(),
                        properties.getFormat(),
                        properties.getCharset(),
                        properties.getAlipayPublicKey(),
                        properties.getSignType());
        this.uriTemplateHandler = new DefaultUriBuilderFactory();
    }

    @Override
    public String capturePayment(PaymentOrder order) throws PaymentException {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        request.setBizModel(model);
        request.setReturnUrl(this.properties.getReturnUrl());
        request.setNotifyUrl(this.properties.getNotifyUrl());
        model.setOutTradeNo(order.getOrderId());
        model.setSubject(order.getTitle());
        model.setTotalAmount(order.getTotalAmount().toString());
        model.setProductCode("QUICK_WAP_WAY");
        model.setTimeoutExpress("20m");
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request, "get");
            order.setTransactionId(response.getTradeNo());
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new PaymentException(e.getErrMsg());
        }
    }

    @Override
    public PaymentConfirmation confirmPayment(Map<String, String> params) throws PaymentException {
        return null;
    }

    @Override
    public boolean supportsPayment(PaymentProvider provider) {
        return PaymentProvider.ALIPAY == provider;
    }
}
