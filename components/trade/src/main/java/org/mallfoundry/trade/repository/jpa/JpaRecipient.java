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

package org.mallfoundry.trade.repository.jpa;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.trade.Recipient;
import org.mallfoundry.trade.RecipientType;
import org.mallfoundry.trade.bank.HolderType;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class JpaRecipient implements Recipient {

    @Column(name = "recipient_number_")
    private String number;

    @Column(name = "recipient_name_")
    private String name;

    @Column(name = "recipient_type_")
    private RecipientType type;

    @Column(name = "recipient_bank_name_")
    private String bankName;

    @Column(name = "recipient_branch_name_")
    private String branchName;

    @Column(name = "recipient_holder_type_")
    private HolderType holderType;

    @Column(name = "recipient_holder_name_")
    private String holderName;
}
