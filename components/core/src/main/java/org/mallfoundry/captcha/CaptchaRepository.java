package org.mallfoundry.captcha;

import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaType;

import java.util.Optional;

public interface CaptchaRepository {

    Captcha create(CaptchaType type);

    Optional<Captcha> findByToken(String token);

    Captcha save(Captcha captcha);

    void deleteByToken(String token);

}
