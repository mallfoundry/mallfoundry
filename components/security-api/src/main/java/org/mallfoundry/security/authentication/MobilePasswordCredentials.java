package org.mallfoundry.security.authentication;

public interface MobilePasswordCredentials extends Credentials {

    @Override
    default GrantType getGrantType() {
        return GrantType.MOBILE_PASSWORD;
    }

    String getCountryCode();

    String getMobile();

    String getPassword();
}
