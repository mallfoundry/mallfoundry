package org.mallfoundry.identity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mallfoundry.rest.identity.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserTests {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        var registration = new UserCreateRequest();
        registration.setMobile("15688477267");
        registration.setEmail("tgioer@163.com");
        registration.setMode(UserRegistration.Mode.MOBILE);
        this.userService.createUser(registration);
    }
}
