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

package com.mallfoundry.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_order")
public class PaymentOrder {

    @Id
    @Column(name = "order_id_")
    @JsonProperty("order_id")
    private String orderId;

    @Column(name = "title_")
    private String title;

    @Column(name = "transaction_id_", unique = true)
    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("total_amount")
    @Column(name = "total_amount_")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_")
    private PaymentProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private PaymentStatus status;

    public PaymentOrder(String orderId, String title, BigDecimal totalAmount, PaymentProvider provider) {
        this.orderId = orderId;
        this.title = title;
        this.totalAmount = totalAmount;
        this.provider = provider;
        this.status = PaymentStatus.PENDING;
    }

    public void success() {
        this.setStatus(PaymentStatus.SUCCESS);
    }

    public void error() {
        this.setStatus(PaymentStatus.ERROR);
    }

    public boolean isPending() {
        return this.status == PaymentStatus.PENDING;
    }
}
