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

package org.mallfoundry.payment.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.payment.DefaultInstrument;
import org.mallfoundry.payment.DefaultPaymentOrder;
import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentInstrument;
import org.mallfoundry.payment.PaymentMethod;
import org.mallfoundry.payment.PaymentOrder;
import org.mallfoundry.payment.PaymentRefund;
import org.mallfoundry.payment.PaymentStatus;
import org.mallfoundry.payment.PaymentSupport;
import org.mallfoundry.payment.repository.jpa.convert.PaymentInstrumentConverter;
import org.mallfoundry.payment.repository.jpa.convert.PaymentOrdersConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_payment")
public class JpaPayment extends PaymentSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "payer_id_")
    private String payerId;

    @Column(name = "payer_")
    private String payer;

    @Column(name = "instrument_")
    @Convert(converter = PaymentInstrumentConverter.class)
    private PaymentInstrument instrument;

    @Column(name = "source_id_")
    private String sourceId;

    @Column(name = "return_url_")
    private String returnUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private PaymentStatus status;

    @Convert(converter = PaymentOrdersConverter.class)
    @Column(name = "orders_", length = 1024 * 4)
    private List<PaymentOrder> orders = new ArrayList<>();

    @OneToMany(targetEntity = JpaPaymentRefund.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id_")
    private List<PaymentRefund> refunds = new ArrayList<>();

    @Column(name = "total_amount_")
    private BigDecimal totalAmount;

    @Column(name = "started_time_")
    private Date startedTime;

    @Column(name = "captured_time_")
    private Date capturedTime;

    public JpaPayment(String id) {
        this.id = id;
    }

    public static JpaPayment of(Payment payment) {
        if (payment instanceof JpaPayment) {
            return (JpaPayment) payment;
        }

        var target = new JpaPayment();
        BeanUtils.copyProperties(payment, target);
        return target;
    }

    @Override
    public BigDecimal getTotalAmount() {
        return Objects.requireNonNullElse(this.totalAmount, BigDecimal.ZERO);
    }

    @Override
    public PaymentInstrument createInstrument(PaymentMethod type) {
        return new DefaultInstrument(type);
    }

    @Override
    public PaymentOrder createOrder(String orderId) {
        return new DefaultPaymentOrder(orderId);
    }

    @Override
    public PaymentRefund createRefund(String refundId) {
        return new JpaPaymentRefund(refundId);
    }
}
