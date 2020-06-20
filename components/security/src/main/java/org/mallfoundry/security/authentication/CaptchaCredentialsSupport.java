package org.mallfoundry.security.authentication;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.security.authentication.CaptchaCredentials;

@Getter
@Setter(AccessLevel.PROTECTED)
public class CaptchaCredentialsSupport implements CaptchaCredentials {

    private String token;

    private String code;

    public CaptchaCredentialsSupport(String token, String code) {
        this.token = token;
        this.code = code;
    }
}
