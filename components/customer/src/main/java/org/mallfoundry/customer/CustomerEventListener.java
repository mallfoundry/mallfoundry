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

import org.mallfoundry.identity.UserCreatedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class CustomerEventListener {

    private final CustomerService customerService;

    public CustomerEventListener(CustomerService customerService) {
        this.customerService = customerService;
    }

    @EventListener
    public void onUserCreated(UserCreatedEvent createdEvent) {
        var user = createdEvent.getUser();
        var customer = this.customerService.createCustomer(user.getId())
                .toBuilder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .gender(Gender.UNKNOWN).build();
        this.customerService.addCustomer(customer);
    }


/*    private String getNickname(User user) {
        if (StringUtils.isNotBlank(user.getNickname())) {
            return user.getNickname();
        }
    }*/

}
