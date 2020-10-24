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
import org.mallfoundry.finance.account.AccountSupport;
import org.mallfoundry.finance.account.Balance;
import org.mallfoundry.finance.account.BusinessType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_financial_account")
public class JpaAccount extends AccountSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type_")
    private BusinessType businessType;

    @OneToMany(targetEntity = JpaBalance.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id_", referencedColumnName = "id_")
    private List<Balance> balances = new ArrayList<>();

    public JpaAccount(String id) {
        this.id = id;
    }

    @Override
    public Balance createBalance(String currency) {
        return new JpaBalance(this.id, currency);
    }
}
