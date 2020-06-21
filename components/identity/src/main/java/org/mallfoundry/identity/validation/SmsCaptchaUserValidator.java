package org.mallfoundry.identity.validation;

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
        if (registration.getMode() == UserRegistration.Mode.MOBILE) {
            var token = registration.getParameter("captcha_token");
            var code = registration.getParameter("captcha_code");
            var checked = this.captchaService.checkCaptcha(token, code);
            if (!checked) {
                throw CaptchaException.INVALID_CAPTCHA;
            }
        }
    }
}
