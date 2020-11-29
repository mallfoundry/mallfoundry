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

package org.mallfoundry.finance;

import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 支付对象对第三方支付网关所涉及的支付对象进行抽象，使得可以使用同一个支付对象来对接第三方支付。
 * <p>支付对象支持合并付款，当需要合并付款的时候就添加多个支付订单。
 *
 * @author Zhi Tang
 */
public interface Payment extends ObjectBuilder.ToBuilder<Payment.Builder> {

    String getId();

    void setId(String id);

    String getPayerId();

    void setPayerId(String payerId);

    String getPayer();

    void setPayer(String payer);

    PaymentInstrument createInstrument(PaymentMethod type);

    PaymentInstrument getInstrument();

    void setInstrument(PaymentInstrument instrument);

    PaymentOrder createOrder(String orderId);

    void addOrder(PaymentOrder order);

    void addOrders(List<PaymentOrder> orders);

    PaymentOrder getOrder(String orderId);

    List<PaymentOrder> getOrders();

    String getReturnUrl();

    void setReturnUrl(String returnUrl);

    PaymentStatus getStatus();

    BigDecimal getTotalAmount();

    String getSourceId();

    void setSourceId(String sourceId);

    Date getStartedTime();

    Date getCapturedTime();

    boolean isStarted();

    boolean isCaptured();

    void start();

    void capture();

    PaymentRefund createRefund(String refundId);

    List<PaymentRefund> getRefunds();

    Optional<PaymentRefund> getRefund(String refundId);

    PaymentRefund applyRefund(PaymentRefund refund);

    void succeedRefund(String refundId);

    void failRefund(String refundId, String failReason);

    interface Builder extends ObjectBuilder<Payment> {

        Builder payerId(String payerId);

        Builder payer(String payer);

        Builder returnUrl(String returnUrl);

        Builder instrument(PaymentInstrument instrument);

        Builder instrument(Consumer<PaymentInstrument> consumer);

        Builder instrument(Function<Payment, PaymentInstrument> function);

        Builder sourceId(String sourceId);

        Builder order(PaymentOrder order);

        Builder order(Consumer<PaymentOrder> consumer);

        Builder order(Function<Payment, PaymentOrder> function);
    }
}
