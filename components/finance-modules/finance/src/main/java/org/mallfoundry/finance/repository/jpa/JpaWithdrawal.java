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

package org.mallfoundry.finance.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.finance.CurrencyCode;
import org.mallfoundry.finance.Recipient;
import org.mallfoundry.finance.WithdrawalStatus;
import org.mallfoundry.finance.WithdrawalSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_financial_withdrawal")
public class JpaWithdrawal extends WithdrawalSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotBlank
    @Column(name = "account_id_")
    private String accountId;

    @Min(0)
    @Column(name = "amount_")
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_")
    private CurrencyCode currency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private WithdrawalStatus status;

    @ManyToOne(targetEntity = JpaRecipient.class)
    @JoinColumn(name = "recipient_id_")
    private Recipient recipient;

    @NotBlank
    @Column(name = "operator_id_")
    private String operatorId;

    @NotBlank
    @Column(name = "operator_")
    private String operator;

    @Column(name = "transaction_id_")
    private String transactionId;

    @NotNull
    @Column(name = "applied_time_")
    private Date appliedTime;

    @Column(name = "disapproval_reason_")
    private String disapprovalReason;

    @Column(name = "disapproved_time_")
    private Date disapprovedTime;

    @Column(name = "canceled_time_")
    private Date canceledTime;

    @Column(name = "approved_time_")
    private Date approvedTime;

    @Column(name = "succeeded_time_")
    private Date succeededTime;

    @Column(name = "failure_reason_")
    private String failureReason;

    @Column(name = "failed_time_")
    private Date failedTime;

    public JpaWithdrawal(String id) {
        this.id = id;
    }

    @Override
    public Recipient createRecipient() {
        return new JpaRecipient();
    }
}
