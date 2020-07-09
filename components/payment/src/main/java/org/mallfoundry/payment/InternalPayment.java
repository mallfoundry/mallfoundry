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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.jpa.convert.StringStringMapConverter;
import org.mallfoundry.payment.repository.jpa.convert.PaymentSourceConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_payment")
public class InternalPayment implements Payment {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "source_")
    @Convert(converter = PaymentSourceConverter.class)
    private PaymentInstrument instrument;

    @Column(name = "reference_")
    private String reference;

    @Column(name = "return_url_")
    private String returnUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private PaymentStatus status;

    @Column(name = "amount_")
    private BigDecimal amount;

    @Lob
    @Column(name = "metadata_")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> metadata;

    @Column(name = "captured_time_")
    private Date capturedTime;

    @Column(name = "created_time_")
    private Date createdTime;

    public InternalPayment(String id) {
        this.id = id;
    }

    public static InternalPayment of(Payment payment) {
        if (payment instanceof InternalPayment) {
            return (InternalPayment) payment;
        }

        var target = new InternalPayment();
        BeanUtils.copyProperties(payment, target);
        return target;
    }

    @Override
    public PaymentInstrument createInstrument(String type) {
        return new InternalInstrument(type);
    }

    @Override
    public void pending() {
        this.setStatus(PaymentStatus.PENDING);
        this.setCreatedTime(new Date());
    }

    @Override
    public void capture() {
        this.setStatus(PaymentStatus.CAPTURED);
        this.setCapturedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }
}
