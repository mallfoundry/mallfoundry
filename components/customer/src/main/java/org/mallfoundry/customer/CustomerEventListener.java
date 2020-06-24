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
                .nickname(user.getNickname())
                .username(user.getUsername())
                .gender(Gender.UNKNOWN).build();
        this.customerService.addCustomer(customer);
    }

}
