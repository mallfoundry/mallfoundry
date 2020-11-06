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

package org.mallfoundry.finance.account.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.finance.CurrencyCode;
import org.mallfoundry.finance.TransactionDirection;
import org.mallfoundry.finance.TransactionType;
import org.mallfoundry.finance.account.BalanceSource;
import org.mallfoundry.finance.account.BalanceTransaction;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_financial_balance_transaction")
public class JpaBalanceTransaction implements BalanceTransaction {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "account_id_")
    private String accountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code_")
    private CurrencyCode currencyCode;

    @Column(name = "amount_")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction_")
    private TransactionDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_")
    private TransactionType type;

    @ElementCollection(targetClass = JpaBalanceSource.class)
    @JoinTable(name = "mf_financial_balance_transaction_source",
            joinColumns = @JoinColumn(name = "transaction_id_", referencedColumnName = "id_"))
    private List<BalanceSource> sources = new ArrayList<>();

    @Column(name = "ending_balance_")
    private BigDecimal endingBalance;

    @Column(name = "memo_")
    private String memo;

    @Column(name = "description_")
    private String description;

    @Column(name = "created_time_")
    private Date createdTime;

    @Override
    public void create() {
        this.setCreatedTime(new Date());
    }
}
