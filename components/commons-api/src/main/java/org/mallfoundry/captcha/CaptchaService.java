package org.mallfoundry.captcha;

public interface CaptchaService {

    Captcha createCaptcha(CaptchaType type);

    Captcha generateCaptcha(CaptchaType type) throws CaptchaException;

    boolean checkCaptcha(String token, String code);
}
