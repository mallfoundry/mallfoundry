package org.mallfoundry.security;

public interface PasswordCredentials extends Credentials {

    @Override
    default GrantType getGrantType() {
        return GrantType.PASSWORD;
    }

    String getUsername();

    String getPassword();
}
