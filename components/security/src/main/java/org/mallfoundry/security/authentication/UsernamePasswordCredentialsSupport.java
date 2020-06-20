package org.mallfoundry.security.authentication;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class UsernamePasswordCredentialsSupport implements UsernamePasswordCredentials {

    private String username;

    private String password;

    public UsernamePasswordCredentialsSupport(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
