package org.mallfoundry.captcha;

import java.util.Optional;

public interface CaptchaService {

    Captcha createCaptcha(CaptchaType type);

    Optional<Captcha> getCaptcha(String token);

    Captcha generateCaptcha(CaptchaType type) throws CaptchaException;

    boolean checkCaptcha(String token, String code);
}
