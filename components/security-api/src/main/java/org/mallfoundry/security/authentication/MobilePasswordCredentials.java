package org.mallfoundry.security.authentication;

public interface MobilePasswordCredentials extends Credentials {

    @Override
    default CredentialsType getType() {
        return CredentialsType.MOBILE_PASSWORD;
    }

    String getCountryCode();

    String getMobile();

    String getPassword();
}
