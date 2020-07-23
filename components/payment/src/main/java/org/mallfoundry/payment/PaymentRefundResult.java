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
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

import static org.mallfoundry.payment.PaymentRefundStatus.FAILED;
import static org.mallfoundry.payment.PaymentRefundStatus.SUCCEEDED;

/**
 * 退款结果，作为支付客户端退款接口的返回值。用于判断是否退款成功。
 *
 * @author Zhi Tang
 */
@Getter
@Setter
public class PaymentRefundResult extends PaymentRefundSupport {
    private String id;
    private String paymentId;
    private String storeId;
    private String orderId;
    private PaymentRefundStatus status;
    private String sourceId;
    private BigDecimal amount;
    private String reason;
    private String failReason;
    private Date appliedTime;
    private Date succeededTime;
    private Date failedTime;

    @Override
    public void succeed() {
        this.setStatus(SUCCEEDED);
    }

    @Override
    public void fail(String failReason) {
        this.setFailReason(failReason);
        this.setStatus(FAILED);
    }

    public PaymentRefundResult(String id) {
        this.id = id;
    }
}
