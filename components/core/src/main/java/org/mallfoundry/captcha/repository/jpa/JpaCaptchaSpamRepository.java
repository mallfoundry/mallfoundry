package org.mallfoundry.captcha.repository.jpa;

import org.mallfoundry.captcha.CaptchaSpam;
import org.mallfoundry.captcha.CaptchaSpamRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCaptchaSpamRepository extends CaptchaSpamRepository, JpaRepository<CaptchaSpam, String> {
}
