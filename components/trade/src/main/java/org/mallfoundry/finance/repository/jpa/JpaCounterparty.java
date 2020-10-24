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
import org.mallfoundry.finance.bank.HolderType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_financial_counterparty")
public class JpaCounterparty implements Counterparty {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "number_")
    private String number;

    @Column(name = "name_")
    private String name;

    @Column(name = "bank_name_")
    private String bankName;

    @Column(name = "branch_name_")
    private String branchName;

    @Enumerated(EnumType.STRING)
    @Column(name = "holder_type_")
    private HolderType holderType;

    @Column(name = "holder_name_")
    private String holderName;
}
