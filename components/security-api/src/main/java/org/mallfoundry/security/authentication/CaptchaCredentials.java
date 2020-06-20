package org.mallfoundry.security.authentication;


// mobile +
// mail @
// username
public interface CaptchaCredentials extends Credentials {

    @Override
    default GrantType getGrantType() {
        return GrantType.CAPTCHA;
    }

    String getCode();

    String getToken();
}
