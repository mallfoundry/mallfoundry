package org.mallfoundry.captcha;


// mall.captcha.type=sms
// mall.captcha.sms.template=xxx
// mall.captcha.sms.signature=xxx
public class SmsCaptchaService implements CaptchaService {

    @Override
    public Captcha createCaptcha(CaptchaType type) {
        return null;
    }

    @Override
    public boolean checkCaptcha(Captcha captcha) {
        return false;
    }
}
