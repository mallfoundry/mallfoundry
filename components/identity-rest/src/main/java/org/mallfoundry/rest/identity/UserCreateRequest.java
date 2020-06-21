package org.mallfoundry.rest.identity;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.identity.User;
import org.mallfoundry.identity.UserRegistration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserCreateRequest implements UserRegistration {
    private String username;
    private String nickname;
    private String password;
    private String mobile;
    private String email;
    private Mode mode;
    private Map<String, String> parameters = new HashMap<>();

    @Override
    public void setParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public User assignToUser(User user) {
        return user.toBuilder().username(this.username)
                .nickname(this.nickname)
                .password(this.password)
                .mobile(this.mobile)
                .email(this.email)
                .build();
    }
}
