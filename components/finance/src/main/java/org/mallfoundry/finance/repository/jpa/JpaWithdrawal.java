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
import org.mallfoundry.finance.Recipient;
import org.mallfoundry.finance.Withdrawal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_financial_withdrawal")
public class JpaWithdrawal implements Withdrawal {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "account_id_")
    private String accountId;

    @Column(name = "amount_")
    private BigDecimal amount;

    @Column(name = "currency_")
    private String currency;

    @Embedded
    private JpaRecipient recipient;

    public JpaWithdrawal(String id) {
        this.id = id;
    }

    @Override
    public Recipient createRecipient() {
        return null;
    }

    @Override
    public Recipient getRecipient() {
        return this.recipient;
    }

    @Override
    public void setRecipient(Recipient recipient) {
        this.recipient = (JpaRecipient) recipient;
    }
}
