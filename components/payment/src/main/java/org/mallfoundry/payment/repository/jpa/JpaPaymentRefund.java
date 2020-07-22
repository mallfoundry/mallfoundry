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
import org.mallfoundry.payment.PaymentRefundStatus;
import org.mallfoundry.payment.PaymentRefundSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_payment_refund")
public class JpaPaymentRefund extends PaymentRefundSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "payment_id_")
    private String paymentId;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "order_id_")
    private String orderId;

    @Column(name = "status_")
    private PaymentRefundStatus status;

    @Column(name = "amount_")
    private BigDecimal amount;

    @Column(name = "reason_")
    private String reason;

    @Column(name = "fail_reason_")
    private String failReason;

    @Column(name = "applied_time_")
    private Date appliedTime;

    @Column(name = "succeeded_time_")
    private Date succeededTime;

    @Column(name = "failed_time_")
    private Date failedTime;

    public JpaPaymentRefund(String id) {
        this.id = id;
    }
}
