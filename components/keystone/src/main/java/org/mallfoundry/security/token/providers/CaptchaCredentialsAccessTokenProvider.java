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

package org.mallfoundry.security.token.providers;

import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaService;
import org.mallfoundry.captcha.CaptchaType;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.authentication.CaptchaCredentials;
import org.mallfoundry.security.authentication.Credentials;
import org.mallfoundry.security.token.AccessTokenId;
import org.mallfoundry.security.token.AccessTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CaptchaCredentialsAccessTokenProvider implements AccessTokenProvider {

    private final CaptchaService captchaService;

    private final UserService userService;

    public CaptchaCredentialsAccessTokenProvider(CaptchaService captchaService, UserService userService) {
        this.captchaService = captchaService;
        this.userService = userService;
    }

    @Override
    public AccessTokenId authenticate(Credentials credentials) throws AuthenticationException {
        var cCredentials = (CaptchaCredentials) credentials;
        String token = cCredentials.getCaptchaToken();
        String code = cCredentials.getCaptchaCode();
        var captcha = this.captchaService.getCaptcha(token);
        if (captcha.getType() != CaptchaType.SMS) {
            throw new BadCredentialsException("Only SMS authentication is supported");
        }
        if (!this.captchaService.checkCaptcha(token, code)) {
            throw new BadCredentialsException("Invalid captcha");
        }
        var countryCode = captcha.getParameter(Captcha.COUNTRY_CODE_PARAMETER_NAME);
        var phone = captcha.getParameter(Captcha.PHONE_PARAMETER_NAME);
        var user = this.userService.findUserByPhone(countryCode, phone)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The phone +%s-%s not found", countryCode, phone)));
        return new AccessTokenId(user.getUsername());
    }

    @Override
    public boolean supports(Credentials credentials) {
        return credentials instanceof CaptchaCredentials;
    }
}
