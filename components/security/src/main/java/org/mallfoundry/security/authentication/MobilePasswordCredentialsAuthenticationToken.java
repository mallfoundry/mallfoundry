package org.mallfoundry.security.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
@Setter
public class MobilePasswordCredentialsAuthenticationToken extends AbstractAuthenticationToken {

    private String countryCode;

    private String mobile;

    private String password;

    public MobilePasswordCredentialsAuthenticationToken(String countryCode, String mobile, String password) {
        super(null);
        this.countryCode = countryCode;
        this.mobile = mobile;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return this.mobile;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

}
