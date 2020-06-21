package org.mallfoundry.captcha;

public interface CaptchaProvider {

    void clearCaptcha(Captcha captcha) throws CaptchaException;

    void generateCaptcha(Captcha captcha) throws CaptchaException;

    boolean supports(Captcha captcha);
}
