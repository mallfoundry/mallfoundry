package org.mallfoundry.captcha.repository.jpa;

import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaRepository;
import org.mallfoundry.captcha.CaptchaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCaptchaRepository implements CaptchaRepository {

    private final JpaCaptchaRepositoryProxy captchaRepository;

    public JpaCaptchaRepository(JpaCaptchaRepositoryProxy captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    @Override
    public Captcha create(CaptchaType type) {
        return new JpaCaptcha(type);
    }

    @Override
    public Optional<Captcha> findByToken(String token) {
        return CastUtils.cast(this.captchaRepository.findById(token));
    }

    @Override
    public Captcha save(Captcha captcha) {
        return this.captchaRepository.save(JpaCaptcha.of(captcha));
    }

    @Override
    public void deleteByToken(String token) {
        this.captchaRepository.deleteById(token);
    }

    @Repository
    interface JpaCaptchaRepositoryProxy extends JpaRepository<JpaCaptcha, String> {
    }
}
