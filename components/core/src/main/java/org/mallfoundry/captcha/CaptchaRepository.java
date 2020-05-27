package org.mallfoundry.captcha;

import java.util.Optional;

public interface CaptchaRepository {

    Captcha create(CaptchaType type);

    Optional<Captcha> findByToken(String token);

    Optional<Captcha> findByMobile(String mobile);

    Captcha save(Captcha captcha);

    void deleteByToken(String token);

    void deleteByMobile(String mobile);

}
