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
import org.mallfoundry.security.access.AccessControlManager;
import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.security.access.Principal;
import org.mallfoundry.security.access.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class CustomerEventListener {

    private final CustomerService customerService;
    private final AccessControlManager accessControlManager;

    public CustomerEventListener(CustomerService customerService, AccessControlManager accessControlManager) {
        this.customerService = customerService;
        this.accessControlManager = accessControlManager;
    }

    @EventListener
    public void onUserCreatedEvent(UserCreatedEvent createdEvent) {
        var user = createdEvent.getUser();
        var customer = this.customerService.createCustomer(user);
        this.customerService.addCustomer(customer);
    }

    @EventListener
    public void onCustomerAddedEvent(CustomerAddedEvent event) {
        var customer = event.getCustomer();
        var customerId = customer.getId();
        var tenantResource = this.accessControlManager.createResource(Resource.TENANT_TYPE, customer.getTenantId());
        var resource = this.accessControlManager.createResource(Resource.CUSTOMER_TYPE, customerId);
        var principal = this.accessControlManager.createPrincipal(Principal.USER_TYPE, customerId);
        var accessControl = this.accessControlManager.getAccessControl(tenantResource)
                .createAccessControl(principal, resource);
        this.accessControlManager.deleteAccessControl(accessControl);
        accessControl.grant(AllAuthorities.CUSTOMER_MANAGE, principal);
        this.accessControlManager.addAccessControl(accessControl);
    }
}
