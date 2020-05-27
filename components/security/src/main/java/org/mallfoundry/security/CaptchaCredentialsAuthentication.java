package org.mallfoundry.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
@Setter
public class CaptchaCredentialsAuthentication extends AbstractAuthenticationToken {

    private String token;

    private String code;

    public CaptchaCredentialsAuthentication(String token, String code) {
        super(null);
        this.token = token;
        this.code = code;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.code;
    }

}
