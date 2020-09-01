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

package org.mallfoundry.identity.validation;

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaException;
import org.mallfoundry.captcha.CaptchaService;
import org.mallfoundry.identity.UserRegistration;
import org.mallfoundry.identity.UserValidator;
import org.mallfoundry.identity.UserValidatorException;
import org.springframework.stereotype.Component;

@Component
public class SmsCaptchaUserValidator implements UserValidator {

    private final CaptchaService captchaService;

    public SmsCaptchaUserValidator(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    public void validateCreateUser(UserRegistration registration) throws UserValidatorException {
        if (registration.getMode() == UserRegistration.Mode.PHONE) {
            var token = registration.getParameter("captcha_token");
            var code = registration.getParameter("captcha_code");
            var captcha = captchaService.getCaptcha(token);
            // 判断手机号码是否一致。
            if (!(StringUtils.equals(registration.getCountryCode(), captcha.getParameter(Captcha.COUNTRY_CODE_PARAMETER_NAME))
                    && StringUtils.equals(registration.getPhone(), captcha.getParameter(Captcha.PHONE_PARAMETER_NAME)))) {
                throw CaptchaException.INVALID_CAPTCHA;
            }
            var checked = this.captchaService.checkCaptcha(token, code);
            if (!checked) {
                throw CaptchaException.INVALID_CAPTCHA;
            }
        }
    }
}
