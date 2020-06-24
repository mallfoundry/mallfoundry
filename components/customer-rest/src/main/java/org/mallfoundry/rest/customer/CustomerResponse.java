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
