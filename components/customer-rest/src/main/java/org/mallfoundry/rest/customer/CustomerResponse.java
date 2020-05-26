package org.mallfoundry.rest.customer;

import org.mallfoundry.customer.Customer;
import org.mallfoundry.customer.Gender;
import lombok.Getter;

import java.util.Date;

@Getter
public class CustomerResponse {

    private final String id;

    private final String userId;

    private final String avatar;

    private final Gender gender;

    private final String nickname;

    private final Date birthday;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.userId = customer.getUserId();
        this.avatar = customer.getAvatar();
        this.gender = customer.getGender();
        this.nickname = customer.getNickname();
        this.birthday = customer.getBirthday();
    }
}
