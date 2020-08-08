/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.security.authentication;

import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaException;
import org.mallfoundry.captcha.CaptchaService;
import org.mallfoundry.captcha.CaptchaType;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.UserDetailsSubject;
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
        var mobile = captcha.getParameter(Captcha.PHONE_PARAMETER_NAME);
        var user = this.userService.getUserByPhone(countryCode, mobile)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The phone %s not found", mobile)));
        var securityUser = new UserDetailsSubject(user);
        return new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CaptchaCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
