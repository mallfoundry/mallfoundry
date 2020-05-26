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

    @Override
    public Captcha createCaptcha(CaptchaType type) {
        return this.captchaRepository.create(type);
    }

    @Transactional
    @Override
    public Captcha generateCaptcha(CaptchaType type) {
        var captcha = this.captchaRepository.create(type).toBuilder().token(this.createToken()).code(this.generateCode()).build();
        this.doGenerateCaptcha(captcha);
        return this.captchaRepository.save(captcha);
    }

    @Override
    public boolean checkCaptcha(String token, String code) {
        var storedCaptcha = this.captchaRepository.findByToken(token).orElseThrow();
        return (storedCaptcha.getCreatedTime().getTime() + storedCaptcha.getExpires()) < System.currentTimeMillis()
                && Objects.equals(code, storedCaptcha.getCode());
    }

    protected abstract void doGenerateCaptcha(Captcha captcha);

    protected String createToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    protected String generateCode() {
        return ThreadLocalRandom.current().ints(this.codeLength).mapToObj(Integer::toString).collect(Collectors.joining());
    }
}
