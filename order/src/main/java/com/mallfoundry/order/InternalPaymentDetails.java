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

package com.mallfoundry.order;

import com.mallfoundry.payment.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class InternalPaymentDetails implements PaymentDetails {

    @Column(name = "payment_id_")
    private String paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider_")
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status_")
    private PaymentStatus status;

    public InternalPaymentDetails(String paymentId, String provider, PaymentStatus status) {
        this.paymentId = paymentId;
        this.provider = provider;
        this.status = status;
    }

    public static InternalPaymentDetails of(PaymentDetails details) {
        if (details instanceof InternalPaymentDetails) {
            return (InternalPaymentDetails) details;
        }
        var target = new InternalPaymentDetails();
        BeanUtils.copyProperties(details, target);
        return target;
    }
}
