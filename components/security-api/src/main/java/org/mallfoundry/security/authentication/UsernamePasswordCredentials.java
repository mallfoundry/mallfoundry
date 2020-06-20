package org.mallfoundry.security.authentication;

public interface UsernamePasswordCredentials extends Credentials {

    @Override
    default GrantType getGrantType() {
        return GrantType.USERNAME_PASSWORD;
    }

    String getUsername();

    String getPassword();
}
