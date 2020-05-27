package org.mallfoundry.security;


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
