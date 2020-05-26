package org.mallfoundry.captcha;

public interface CaptchaService {

    Captcha createCaptcha(CaptchaType type);

    boolean checkCaptcha(Captcha captcha);
}
