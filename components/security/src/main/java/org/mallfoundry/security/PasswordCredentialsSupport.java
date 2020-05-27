package org.mallfoundry.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class PasswordCredentialsSupport implements PasswordCredentials {

    private String username;

    private String password;

    public PasswordCredentialsSupport(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
