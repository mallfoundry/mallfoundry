package org.mallfoundry.captcha;

import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class AbstractCaptchaService implements CaptchaService {

    private final CaptchaAntiSpamService captchaAntiSpamService;

    protected final CaptchaRepository captchaRepository;

    @Setter
    private int codeLength = 6;

    public AbstractCaptchaService(CaptchaAntiSpamService captchaAntiSpamService,
                                  CaptchaRepository captchaRepository) {
        this.captchaAntiSpamService = captchaAntiSpamService;
        this.captchaRepository = captchaRepository;
    }

    @Override
    public Captcha createCaptcha(CaptchaType type) {
        return this.captchaRepository.create(type);
    }

    private void checkCaptcha(Captcha captcha) throws CaptchaException {
        var checked = this.captchaAntiSpamService.checkCaptcha(captcha);
        if (!checked) {
            throw new CaptchaException("Too often");
        }
    }

    @Transactional
    @Override
    public Captcha generateCaptcha(Captcha captcha) throws CaptchaException {
        var codeCaptcha = captcha.toBuilder().token(this.createToken()).expires(60 * 1000).code(this.generateCode()).build();
        this.checkCaptcha(codeCaptcha);
        this.doGenerateCaptcha(codeCaptcha);
        Captcha savedCaptcha = this.captchaRepository.save(codeCaptcha);
        this.captchaAntiSpamService.forceCaptcha(savedCaptcha);
        return savedCaptcha;
    }

    @Override
    public boolean checkCaptcha(String token, String code) {
        var captcha = this.captchaRepository.findByToken(token).orElseThrow();
        if (captcha.checkCode(code)) {
            this.captchaRepository.deleteByToken(token);
            return true;
        }
        return false;
    }

    protected abstract void doGenerateCaptcha(Captcha captcha) throws CaptchaException;

    protected String createToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    protected String generateCode() {
        return ThreadLocalRandom.current().ints(this.codeLength, 0, 10)
                .mapToObj(Integer::toString).collect(Collectors.joining());
    }
}
