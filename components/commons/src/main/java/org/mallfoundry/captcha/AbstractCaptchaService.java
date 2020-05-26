package org.mallfoundry.captcha;

import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class AbstractCaptchaService implements CaptchaService {

    protected final CaptchaRepository captchaRepository;

    @Setter
    private int codeLength = 6;

    public AbstractCaptchaService(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    @Transactional
    @Override
    public Captcha createCaptcha(CaptchaType type) {
        var captcha = this.captchaRepository.create(type).toBuilder().token(this.createToken()).code(this.generateCode()).build();
        this.invokeCaptcha(captcha);
        return this.captchaRepository.save(captcha);
    }

    @Override
    public boolean checkCaptcha(Captcha captcha) {
        var storedCaptcha = this.captchaRepository.findByToken(captcha.getToken()).orElseThrow();
        return (storedCaptcha.getCreatedTime().getTime() + captcha.getExpires()) < System.currentTimeMillis()
                && Objects.equals(captcha.getType(), storedCaptcha.getType())
                && Objects.equals(captcha.getToken(), storedCaptcha.getToken())
                && Objects.equals(captcha.getCode(), storedCaptcha.getCode());
    }

    protected abstract void invokeCaptcha(Captcha captcha);

    protected String createToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    protected String generateCode() {
        return ThreadLocalRandom.current().ints(this.codeLength).mapToObj(Integer::toString).collect(Collectors.joining());
    }
}
