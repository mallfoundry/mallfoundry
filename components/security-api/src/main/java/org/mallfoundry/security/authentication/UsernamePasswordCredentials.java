package org.mallfoundry.security.authentication;

public interface UsernamePasswordCredentials extends Credentials {

    @Override
    default CredentialsType getType() {
        return CredentialsType.USERNAME_PASSWORD;
    }

    String getUsername();

    String getPassword();
}
