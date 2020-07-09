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

package org.mallfoundry.rest.customer;

import lombok.Getter;
import org.mallfoundry.customer.Customer;

@Getter
public class CustomerResponse extends CustomerRequest {

    private final String id;

    private final String username;

    private final String nickname;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.setAvatar(customer.getAvatar());
        this.setGender(customer.getGender());
        this.nickname = customer.getNickname();
        this.setBirthdate(customer.getBirthdate());
    }
}
