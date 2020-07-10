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

package org.mallfoundry.payment.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.payment.InternalPaymentNotification;
import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentClient;
import org.mallfoundry.payment.PaymentException;
import org.mallfoundry.payment.PaymentNotification;
import org.mallfoundry.payment.PaymentStatus;
import org.mallfoundry.payment.PaymentMethod;
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
            request.setReturnUrl(UriComponentsBuilder
                    .fromHttpUrl(this.properties.getReturnUrl())
                    .build(Map.of("payment_id", payment.getId())).toString());
        }

        if (StringUtils.isNotBlank(this.properties.getNotifyUrl())) {
            request.setNotifyUrl(UriComponentsBuilder
                    .fromHttpUrl(this.properties.getNotifyUrl())
                    .build(Map.of("payment_id", payment.getId())).toString());
        }

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

    @Override
    public PaymentNotification createPaymentNotification(Object parameterObject) throws PaymentException {
        return new InternalPaymentNotification(parameterObject);
    }

    @Override
    public void validateNotification(PaymentNotification notification) {
        Map<String, String> parameters = notification.getParameters();
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(parameters,
                    this.properties.getAlipayPublicKey(),
                    this.properties.getCharset(),
                    this.properties.getSignType());

            if (!signVerified) {
                throw new PaymentException("Call alipay SDK to verify the signature is bad.");
            }

            String tradeStatus = parameters.get("trade_status");
            if ("WAIT_BUYER_PAY".equals(tradeStatus)) {
                notification.setStatus(PaymentStatus.PENDING);
            } else if ("TRADE_SUCCESS".equals(tradeStatus)
                    || "TRADE_FINISHED".equals(tradeStatus)) {
                notification.setStatus(PaymentStatus.CAPTURED);
            }
            notification.setResult("success".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            notification.setResult("fail".getBytes());
        }
    }

    @Override
    public boolean supportsPayment(Payment payment) {
        return PaymentMethod.ALIPAY.equals(payment.getInstrument().getType());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
