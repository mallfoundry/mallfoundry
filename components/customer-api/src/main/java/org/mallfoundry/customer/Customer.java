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

package org.mallfoundry.customer;

import org.mallfoundry.identity.Gender;
import org.mallfoundry.identity.TenantOwnership;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Customer extends TenantOwnership, ObjectBuilder.ToBuilder<Customer.Builder> {

    String getId();

    String getUsername();

    void setUsername(String username);

    String getAvatar();

    void setAvatar(String avatar);

    String getNickname();

    void setNickname(String nickname);

    Gender getGender();

    void setGender(Gender gender);

    Date getBirthdate();

    void setBirthdate(Date birthdate);

    CustomerAddress createAddress(String id);

    List<CustomerAddress> getAddresses();

    Optional<CustomerAddress> getDefaultAddress();

    Optional<CustomerAddress> getAddress(String id);

    void addAddress(CustomerAddress address);

    void updateAddress(CustomerAddress address);

    void removeAddress(CustomerAddress address);

    interface Builder extends ObjectBuilder<Customer> {

        Builder avatar(String avatar);

        Builder nickname(String nickname);

        Builder gender(Gender gender);

        Builder birthdate(Date birthdate);
    }
}
