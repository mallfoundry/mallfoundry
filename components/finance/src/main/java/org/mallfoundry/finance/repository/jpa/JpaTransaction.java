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
import org.mallfoundry.finance.Counterparty;
import org.mallfoundry.finance.TransactionDirection;
import org.mallfoundry.finance.TransactionStatus;
import org.mallfoundry.finance.TransactionSupport;
import org.mallfoundry.finance.TransactionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_financial_transaction")
public class JpaTransaction extends TransactionSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "account_id_")
    private String accountId;

    @ManyToOne(targetEntity = JpaCounterparty.class)
    @JoinColumn(name = "counterparty_id_")
    private Counterparty counterparty;

    @Column(name = "amount_")
    private BigDecimal amount;

    @Column(name = "currency_")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction_")
    private TransactionDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_")
    private TransactionType type;

    @Column(name = "description_")
    private String description;

    @Column(name = "source_id_")
    private String sourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private TransactionStatus status;

    @Column(name = "memo_")
    private String memo;

    @Column(name = "created_time_")
    private Date createdTime;

    public JpaTransaction(String id) {
        this.id = id;
    }

}
