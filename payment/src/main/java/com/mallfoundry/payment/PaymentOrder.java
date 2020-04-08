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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mallfoundry.data.jpa.convert.StringListConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "payment_order")
public class PaymentOrder {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id_")
    private Long id;

    @Convert(converter = StringListConverter.class)
    @Column(name = "orders_")
    private List<String> orders = new ArrayList<>();

    @JsonProperty(value = "payer_id", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "payer_id_")
    private String payerId;

    @Column(name = "title_")
    private String title;

    @Column(name = "transaction_id_", unique = true)
    @JsonProperty(value = "transaction_id", access = JsonProperty.Access.READ_ONLY)
    private String transactionId;

    @JsonProperty(value = "total_amount", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "total_amount_")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_")
    private PaymentProviderType provider;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private PaymentStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "created_time", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "created_time_")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public void pending() {
        this.setStatus(PaymentStatus.PENDING);
        this.setCreatedTime(new Date());
    }

    public boolean isPending() {
        return this.status == PaymentStatus.PENDING;
    }

    public boolean isSuccess() {
        return this.status == PaymentStatus.SUCCESS;
    }

    public static PaymentOrderBuilder builder() {
        return new PaymentOrderBuilder(new PaymentOrder());
    }

    public static class PaymentOrderBuilder {

        private final PaymentOrder order;

        public PaymentOrderBuilder(PaymentOrder order) {
            this.order = order;
            this.order.pending();
        }

        public PaymentOrderBuilder title(String title) {
            this.order.setTitle(title);
            return this;
        }

        public PaymentOrderBuilder orders(List<String> orders) {
            this.order.setOrders(orders);
            return this;
        }

        public PaymentOrderBuilder transactionId(String transactionId) {
            this.order.setTransactionId(transactionId);
            return this;
        }

        public PaymentOrderBuilder totalAmount(BigDecimal totalAmount) {
            this.order.setTotalAmount(totalAmount);
            return this;
        }

        public PaymentOrderBuilder totalAmount(String totalAmount) {
            this.order.setTotalAmount(new BigDecimal(totalAmount));
            return this;
        }

        public PaymentOrderBuilder totalAmount(double totalAmount) {
            this.order.setTotalAmount(BigDecimal.valueOf(totalAmount));
            return this;
        }

        public PaymentOrderBuilder provider(PaymentProviderType provider) {
            this.order.setProvider(provider);
            return this;
        }

        public PaymentOrder build() {
            return this.order;
        }
    }
}
