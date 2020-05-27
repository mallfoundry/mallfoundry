package org.mallfoundry.security;

public class DefaultCaptchaCredentials extends CaptchaCredentialsSupport {

    public DefaultCaptchaCredentials(String token, String code) {
        super(token, code);
    }
}
