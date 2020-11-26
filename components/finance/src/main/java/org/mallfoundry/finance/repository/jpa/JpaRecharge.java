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
import org.mallfoundry.finance.Recharge;
import org.mallfoundry.finance.RechargeStatus;
import org.mallfoundry.finance.RechargeSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
@Table(name = "mf_financial_recharge")
public class JpaRecharge extends RechargeSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotBlank
    @Column(name = "account_id_")
    private String accountId;

    @Column(name = "operator_")
    private String operator;

    @Column(name = "operator_id_")
    private String operatorId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code_")
    private CurrencyCode currencyCode;

    @Min(0)
    @Column(name = "amount_")
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private RechargeStatus status;

    @NotNull
    @Column(name = "created_time_")
    private Date createdTime;

    @Column(name = "canceled_time_")
    private Date canceledTime;

    @Column(name = "succeeded_time_")
    private Date succeededTime;

    @Column(name = "failure_reason_")
    private String failureReason;

    @Column(name = "failed_time_")
    private Date failedTime;

    public JpaRecharge(@NotBlank String id) {
        this.id = id;
    }

    public static JpaRecharge of(Recharge recharge) {
        if (recharge instanceof JpaRecharge) {
            return (JpaRecharge) recharge;
        }
        var target = new JpaRecharge();
        BeanUtils.copyProperties(recharge, target);
        return target;
    }
}
