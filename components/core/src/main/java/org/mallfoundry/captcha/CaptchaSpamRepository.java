package org.mallfoundry.captcha;

import java.util.Optional;

public interface CaptchaSpamRepository {

    Optional<CaptchaSpam> findById(String id);

    CaptchaSpam save(CaptchaSpam spam);

    void deleteById(String id);
}
