package org.mallfoundry.captcha;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class CaptchaAntiSpamService {

    private final CaptchaSpamRepository captchaSpamRepository;

    public CaptchaAntiSpamService(CaptchaSpamRepository captchaSpamRepository) {
        this.captchaSpamRepository = captchaSpamRepository;
    }

    private CaptchaSpam createCaptchaSpam(Captcha captcha) throws CaptchaException {
        if (Objects.equals(captcha.getType(), CaptchaType.SMS)) {
            var mobile = captcha.getParameter(Captcha.MOBILE_PARAMETER_NAME);
            return new CaptchaSpam(mobile, captcha.getExpires(), captcha.getCreatedTime());
        }

        throw new CaptchaException(String.format("This type(%s) of captcha is not supported", captcha.getType()));
    }

    private Optional<CaptchaSpam> getCaptchaSpam(Captcha captcha) {
        var spam = this.createCaptchaSpam(captcha);
        return this.captchaSpamRepository.findById(spam.getId());
    }

    public boolean checkCaptcha(Captcha captcha) {
        return this.getCaptchaSpam(captcha).map(CaptchaSpam::isExpired).orElse(true);
    }

    @Transactional
    public void forceCaptcha(Captcha captcha) {
        var spam = this.createCaptchaSpam(captcha);
        this.captchaSpamRepository.save(spam);
    }
}
