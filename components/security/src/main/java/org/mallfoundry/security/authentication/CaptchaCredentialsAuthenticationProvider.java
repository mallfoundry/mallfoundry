package org.mallfoundry.security.authentication;

import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaException;
import org.mallfoundry.captcha.CaptchaService;
import org.mallfoundry.captcha.CaptchaType;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.UserSubject;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CaptchaCredentialsAuthenticationProvider implements AuthenticationProvider {

    private final CaptchaService captchaService;

    private final UserService userService;

    public CaptchaCredentialsAuthenticationProvider(CaptchaService captchaService, UserService userService) {
        this.captchaService = captchaService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        String code = (String) authentication.getCredentials();
        var captcha = this.captchaService.getCaptcha(token)
                .orElseThrow(() -> CaptchaException.INVALID_CAPTCHA);
        if (captcha.getType() != CaptchaType.SMS) {
            throw new BadCredentialsException("Only SMS authentication is supported");
        }
        if (!this.captchaService.checkCaptcha(token, code)) {
            throw new BadCredentialsException("Invalid captcha");
        }
        var countryCode = captcha.getParameter(Captcha.COUNTRY_CODE_PARAMETER_NAME);
        var mobile = captcha.getParameter(Captcha.MOBILE_PARAMETER_NAME);
        var user = this.userService.getUserByMobile(countryCode, mobile)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The mobile %s not found", mobile)));
        var securityUser = new UserSubject(user);
        return new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CaptchaCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
