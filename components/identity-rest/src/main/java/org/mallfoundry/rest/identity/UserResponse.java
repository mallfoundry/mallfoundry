package org.mallfoundry.rest.identity;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.identity.User;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UserResponse {

    private String id;

    private String username;

    private String nickname;

    private String countryCode;

    private String mobile;

    private String email;

    private List<String> authorities;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.countryCode = user.getCountryCode();
        this.mobile = user.getMobile();
        this.email = user.getEmail();
        this.authorities = CollectionUtils.isEmpty(user.getAuthorities())
                ? Collections.emptyList()
                : Collections.unmodifiableList(user.getAuthorities());
    }

    public static UserResponse of(User user) {
        return new UserResponse(user);
    }
}
