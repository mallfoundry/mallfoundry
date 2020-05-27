package org.mallfoundry.captcha.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface JpaCaptchaRepositoryDelegate extends JpaRepository<JpaCaptcha, String> {

    Optional<JpaCaptcha> findByMobile(String mobile);

    void deleteByMobile(String mobile);
}
