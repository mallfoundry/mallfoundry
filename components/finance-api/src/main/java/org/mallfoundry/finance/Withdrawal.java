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

public interface Withdrawal extends Serializable, ObjectBuilder.ToBuilder<Withdrawal.Builder> {

    String getId();

    void setId(String id);

    String getAccountId();

    void setAccountId(String accountId);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    String getCurrency();

    void setCurrency(String currency);

    WithdrawalStatus getStatus();

    Recipient createRecipient();

    Recipient getRecipient();

    void setRecipient(Recipient recipient);

    Date getAppliedTime();

    String getDisapprovalReason();

    Date getDisapprovedTime();

    Date getCanceledTime();

    Date getApprovedTime();

    Date getSucceededTime();

    String getFailureReason();

    Date getFailedTime();

    void apply();

    void disapprove(String disapprovalReason);

    void cancel();

    void approve();

    void succeed();

    void fail(String failureReason);

    interface Builder extends ObjectBuilder<Withdrawal> {

        Builder accountId(String accountId);

        Builder amount(BigDecimal amount);

        Builder currency(String currency);

        Builder recipient(Recipient recipient);
    }
}
