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

package org.mallfoundry.thirdpay.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.finance.Payment;
import org.mallfoundry.finance.PaymentException;
import org.mallfoundry.finance.PaymentMethod;
import org.mallfoundry.finance.PaymentNotification;
import org.mallfoundry.finance.PaymentRefund;
import org.mallfoundry.finance.PaymentRefundException;
import org.mallfoundry.thirdpay.PaymentClient;
import org.mallfoundry.thirdpay.PaymentRefundResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.util.CastUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

@Setter
@Getter
public class AlipayClient implements PaymentClient, InitializingBean {

    private String serverUrl;

    private String appId;

    private String alipayPublicKey;

    private String appPrivateKey;

    private String charset;

    private String format;

    private String signType;

    private String returnUrl;

    private String notifyUrl;

    private com.alipay.api.AlipayClient client;

    @Override
    public void afterPropertiesSet() {
        if (Objects.isNull(this.client)) {
            this.client =
                    new DefaultAlipayClient(this.getServerUrl(),
                            this.getAppId(), this.getAppPrivateKey(),
                            this.getFormat(), this.getCharset(),
                            this.getAlipayPublicKey(), this.getSignType());
        }
    }

    @Override
    public String redirectPaymentUrl(Payment payment) throws PaymentException {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        if (StringUtils.isNotBlank(this.getReturnUrl())) {
            request.setReturnUrl(UriComponentsBuilder
                    .fromHttpUrl(this.getReturnUrl())
                    .build(Map.of("payment_id", payment.getId())).toString());
        }

        if (StringUtils.isNotBlank(this.getNotifyUrl())) {
            request.setNotifyUrl(UriComponentsBuilder
                    .fromHttpUrl(this.getNotifyUrl())
                    .build(Map.of("payment_id", payment.getId())).toString());
        }
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(String.valueOf(payment.getId()));
        model.setSubject("Mall-Foundry-APP");
        model.setTotalAmount(String.valueOf(payment.getTotalAmount()));
        model.setProductCode("QUICK_WAP_WAY");
        model.setTimeoutExpress("20m");
        // Set biz model.
        request.setBizModel(model);
        try {
            AlipayTradeWapPayResponse response = this.client.pageExecute(request, "get");
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new PaymentException(e);
        }
    }

    public PaymentNotification createPaymentNotification(Object parameterObject) throws PaymentException {
        return new AlipayNotification(CastUtils.cast(parameterObject));
    }

    @Override
    public PaymentNotification validateNotification(Object parameters) {
        var notification = this.createPaymentNotification(parameters);
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(notification.getParameters(),
                    this.getAlipayPublicKey(), this.getCharset(), this.getSignType());
            if (!signVerified) {
                throw new PaymentException("Call alipay SDK to verify the signature is bad");
            }
            String tradeStatus = notification.getParameter("trade_status");
            if ("WAIT_BUYER_PAY".equals(tradeStatus)) {
                notification.pending();
            } else if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                notification.capture();
            }
            notification.setResult("success".getBytes());
        } catch (Exception e) {
            notification.setResult("fail".getBytes());
            e.printStackTrace();
        }
        return notification;
    }

    @Override
    public PaymentRefund refundPayment(Payment payment, PaymentRefund refund) {
        var request = new AlipayTradeRefundRequest();
        var model = new AlipayTradeRefundModel();
        model.setTradeNo(payment.getSourceId());
        model.setOutTradeNo(payment.getId());
        model.setRefundAmount(String.valueOf(refund.getAmount()));
        model.setRefundReason(refund.getReason());
        model.setOutRequestNo(refund.getId());
        request.setBizModel(model);
        var result = new PaymentRefundResult(refund.getId());
        try {
            var response = this.client.execute(request);
            if (Objects.equals("20000", response.getCode())) {
                throw new PaymentRefundException(String.format("%s: %s", response.getSubCode(), response.getSubMsg()));
            }
            result.succeed();
        } catch (Exception e) {
            result.fail(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean supportsPayment(Payment payment) {
        return PaymentMethod.ALIPAY.equals(payment.getInstrument().getType());
    }
}
