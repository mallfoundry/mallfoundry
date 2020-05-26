package org.mallfoundry.captcha;

public interface CaptchaService {

    Captcha createCaptcha(CaptchaType type);

    Captcha generateCaptcha(CaptchaType type);

    boolean checkCaptcha(String token, String code);
}
