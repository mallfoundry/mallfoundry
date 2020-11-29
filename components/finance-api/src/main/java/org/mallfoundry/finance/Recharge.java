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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface Recharge extends Serializable, ObjectBuilder.ToBuilder<Recharge.Builder> {

    String getId();

    void setId(String id);

    String getAccountId();

    void setAccountId(String accountId);

    String getOperatorId();

    void setOperatorId(String operatorId);

    String getOperator();

    void setOperator(String operator);

    CurrencyCode getCurrencyCode();

    void setCurrencyCode(CurrencyCode currencyCode);

     PaymentMethod getPaymentMethod();

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    RechargeStatus getStatus();

    Date getCreatedTime();

    Date getCanceledTime();

    Date getSucceededTime();

    String getFailureReason();

    Date getFailedTime();

    void create();

    void cancel();

    /*void pay(Payment payment);*/

    void succeed();

    void fail(String failureReason);

    interface Builder extends ObjectBuilder<Recharge> {

        Builder accountId(String accountId);

        Builder currencyCode(CurrencyCode currencyCode);

        Builder amount(BigDecimal amount);
    }
}
