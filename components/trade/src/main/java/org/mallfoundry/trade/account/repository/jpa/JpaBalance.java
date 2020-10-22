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

package org.mallfoundry.trade.account.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.trade.account.BalanceSource;
import org.mallfoundry.trade.account.BalanceSupport;
import org.mallfoundry.trade.SourceType;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_trade_balance")
@IdClass(JpaBalanceId.class)
public class JpaBalance extends BalanceSupport {

    @Id
    @Column(name = "account_id_")
    private String accountId;

    @Id
    @Column(name = "currency_")
    private String currency;

    @Column(name = "pending_amount_")
    private BigDecimal pendingAmount = BigDecimal.ZERO;

    @Column(name = "available_amount_")
    private BigDecimal availableAmount = BigDecimal.ZERO;

    @ElementCollection(targetClass = JpaBalanceSource.class)
    @JoinTable(name = "mf_trade_balance_source",
            joinColumns = {
                    @JoinColumn(name = "account_id_", referencedColumnName = "account_id_"),
                    @JoinColumn(name = "currency_", referencedColumnName = "currency_")
            })
    private List<BalanceSource> sources = new ArrayList<>();

    public JpaBalance(String accountId, String currency) {
        this.accountId = accountId;
        this.currency = currency;
    }

    @Override
    public BalanceSource createSource(SourceType sourceType) {
        return new JpaBalanceSource(sourceType);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaBalance)) {
            return false;
        }
        JpaBalance that = (JpaBalance) object;
        return Objects.equals(accountId, that.accountId)
                && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, currency);
    }
}
