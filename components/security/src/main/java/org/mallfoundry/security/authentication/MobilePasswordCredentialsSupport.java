package org.mallfoundry.security.authentication;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.security.authentication.MobilePasswordCredentials;

@Getter
@Setter(AccessLevel.PROTECTED)
public class MobilePasswordCredentialsSupport implements MobilePasswordCredentials {

    private String countryCode;

    private String mobile;

    private String password;

    public MobilePasswordCredentialsSupport(String countryCode, String mobile, String password) {
        this.countryCode = countryCode;
        this.mobile = mobile;
        this.password = password;
    }
}
