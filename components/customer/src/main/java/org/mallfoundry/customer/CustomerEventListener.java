package org.mallfoundry.customer;

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.identity.User;
import org.mallfoundry.identity.UserCreatedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class CustomerEventListener {

    private final CustomerConfiguration customerConfiguration;

    private final CustomerService customerService;

    public CustomerEventListener(CustomerConfiguration customerConfiguration, CustomerService customerService) {
        this.customerConfiguration = customerConfiguration;
        this.customerService = customerService;
    }

    @EventListener
    public void onUserCreated(UserCreatedEvent createdEvent) {
        var user = createdEvent.getUser();
        var customer = this.customerService.createCustomer(user.getId())
                .toBuilder()
                .avatar(this.customerConfiguration.getDefaultAvatar())
                .nickname("用户-" + user.getId())
                .username(user.getUsername())
                .gender(Gender.UNKNOWN).build();
        this.customerService.addCustomer(customer);
    }


/*    private String getNickname(User user) {
        if (StringUtils.isNotBlank(user.getNickname())) {
            return user.getNickname();
        }
    }*/

}
