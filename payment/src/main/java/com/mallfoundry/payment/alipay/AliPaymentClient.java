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
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.mallfoundry.payment.Payment;
import com.mallfoundry.payment.PaymentClient;
import com.mallfoundry.payment.AsyncConfirmation;
import com.mallfoundry.payment.PaymentException;
import com.mallfoundry.payment.PaymentProviderType;
import com.mallfoundry.payment.PaymentStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class AliPaymentClient implements PaymentClient {

    private final AliPaymentProperties properties;

    private final AlipayClient alipayClient;

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
    }

    @Override
    public String createPaymentRedirectUrl(Payment payment) throws PaymentException {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        if (StringUtils.isNotBlank(this.properties.getReturnUrl())) {
            request.setReturnUrl(this.properties.getReturnUrl());
        }

        request.setNotifyUrl(UriComponentsBuilder
                .fromHttpUrl(this.properties.getNotifyUrl())
                .build(Map.of("id", payment.getId())).toString());
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(String.valueOf(payment.getId()));
        model.setSubject("Mall-Foundry-APP");
        model.setTotalAmount(String.valueOf(payment.getAmount()));
        model.setProductCode("QUICK_WAP_WAY");
        model.setTimeoutExpress("20m");
        // Set biz model.
        request.setBizModel(model);
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request, "get");
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new PaymentException(e);
        }
    }

    // async-approve
    @Override
    public AsyncConfirmation confirmPayment(Map<String, String> params) {

        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params,
                    this.properties.getAlipayPublicKey(),
                    this.properties.getCharset(),
                    this.properties.getSignType());

            if (!signVerified) {
                throw new PaymentException("Call alipay SDK to verify the signature is bad.");
            }

            String orderId = params.get("out_trade_no");
            String transactionId = params.get("trade_no");
            String tradeStatus = params.get("trade_status");

            AsyncConfirmation confirmation = new AsyncConfirmation();
            confirmation.setOrderId(orderId);
            confirmation.setTransactionId(transactionId);
            if ("WAIT_BUYER_PAY".equals(tradeStatus)) {
                confirmation.setStatus(PaymentStatus.PENDING);
            } else if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                confirmation.setStatus(PaymentStatus.SUCCESS);
            }

            confirmation.setBody("success");
            return confirmation;
        } catch (Exception e) {
            e.printStackTrace();
            AsyncConfirmation confirmation = new AsyncConfirmation();
            confirmation.setStatus(PaymentStatus.FAILURE);
            confirmation.setBody("fail");
            return confirmation;
        }
    }

    @Override
    public boolean supportsPayment(PaymentProviderType provider) {
        return PaymentProviderType.ALIPAY == provider;
    }

    @Override
    public boolean supportsPayment(Payment payment) {
        return "alipay".equalsIgnoreCase(payment.getSource().getType());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
