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

import org.mallfoundry.finance.bank.HolderType;
import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;

public interface Recipient extends Serializable, ObjectBuilder.ToBuilder<Recipient.Builder> {

    String getId();

    void setId(String id);

    String getNumber();

    void setNumber(String number);

    String getName();

    void setName(String name);

    RecipientType getType();

    void setType(RecipientType type);

    String getBankName();

    void setBankName(String bankName);

    String getBranchName();

    void setBranchName(String branchName);

    HolderType getHolderType();

    void setHolderType(HolderType holderType);

    String getHolderName();

    void setHolderName(String holderName);

    interface Builder extends ObjectBuilder<Recipient> {

        Builder number(String number);

        Builder name(String name);

        Builder type(RecipientType type);

        Builder bankName(String bankName);

        Builder branchName(String branchName);

        Builder holderType(HolderType holderType);

        Builder holderName(String holderName);
    }
}
