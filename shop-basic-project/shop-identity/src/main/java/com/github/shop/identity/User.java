package com.github.shop.identity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private long uid;

    private String nickname;

    /**
     * Username of this user.
     */
    private String username;

    private String password;

    private String email;

    private boolean enabled;
}
