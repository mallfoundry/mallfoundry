package org.mallfoundry.security.authentication;

public class DefaultMobilePasswordCredentials extends MobilePasswordCredentialsSupport {
    public DefaultMobilePasswordCredentials(String countryCode, String mobile, String password) {
        super(countryCode, mobile, password);
    }
}
