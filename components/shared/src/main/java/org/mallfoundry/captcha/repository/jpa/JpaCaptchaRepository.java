/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.captcha.repository.jpa;

import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaRepository;
import org.mallfoundry.captcha.CaptchaType;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCaptchaRepository implements CaptchaRepository {

    private final JpaCaptchaRepositoryDelegate captchaRepository;

    public JpaCaptchaRepository(JpaCaptchaRepositoryDelegate captchaRepository) {
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
    public Optional<Captcha> findByCountryCodeAndPhone(String countryCode, String phone) {
        return CastUtils.cast(this.captchaRepository.findByCountryCodeAndPhone(countryCode, phone));
    }

    @Override
    public Captcha save(Captcha captcha) {
        return this.captchaRepository.save(JpaCaptcha.of(captcha));
    }

    @Override
    public void deleteByToken(String token) {
        this.captchaRepository.deleteById(token);
    }

    @Override
    public void deleteByCountryCodeAndPhone(String countryCode, String phone) {
        this.captchaRepository.deleteByCountryCodeAndPhone(countryCode, phone);
    }

}
