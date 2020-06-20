package org.mallfoundry.captcha;

import java.util.Optional;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface CaptchaService {

    Captcha createCaptcha(CaptchaType type);

    Optional<Captcha> getCaptcha(String token);

    Captcha generateCaptcha(Captcha captcha) throws CaptchaException;

    boolean checkCaptcha(String token, String code);
}
