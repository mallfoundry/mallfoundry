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

package org.mallfoundry.captcha;

import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class AbstractCaptchaService implements CaptchaService {

    private final CaptchaAntiSpamService captchaAntiSpamService;

    protected final CaptchaRepository captchaRepository;

    private static final int DEFAULT_EXPIRES = 10 * 1000;

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

    @Override
    public Captcha getCaptcha(String token) {
        return this.captchaRepository.findByToken(token)
                .map(this::resultCaptcha)
                .orElseThrow(() -> CaptchaException.INVALID_CAPTCHA);
    }

    private void spamCaptcha(Captcha captcha) throws CaptchaException {
        var spammed = this.captchaAntiSpamService.spamCaptcha(captcha);
        if (spammed) {
            throw new CaptchaException("Too often");
        }
    }

    private void scoreCaptcha(Captcha captcha) throws CaptchaException {
        this.captchaAntiSpamService.scoreCaptcha(captcha);
    }

    private Captcha resultCaptcha(Captcha captcha) {
        return this.createCaptcha(captcha.getType())
                .toBuilder().token(captcha.getToken()).parameters(captcha.getParameters())
                .expires(captcha.getExpires()).intervals(captcha.getIntervals())
                .createdTime(captcha.getCreatedTime())
                .build();
    }

    @Transactional
    @Override
    public Captcha generateCaptcha(Captcha captcha) throws CaptchaException {
        var codeCaptcha = captcha.toBuilder().token(this.createToken()).expires(DEFAULT_EXPIRES).code(this.generateCode()).build();
        this.spamCaptcha(codeCaptcha);
        this.doClearCaptcha(codeCaptcha);
        this.doGenerateCaptcha(codeCaptcha);
        Captcha savedCaptcha = this.captchaRepository.save(codeCaptcha);
        this.scoreCaptcha(savedCaptcha);
        return this.resultCaptcha(savedCaptcha);
    }

    private void clearCheckedCaptcha(Captcha captcha) throws CaptchaException {
        this.captchaRepository.deleteByToken(captcha.getToken());
        this.captchaAntiSpamService.clearCaptcha(captcha);
    }

    @Override
    public boolean checkCaptcha(String token, String code) {
        var captcha = this.captchaRepository.findByToken(token)
                .orElseThrow(() -> new CaptchaException("Invalid captcha"));
        if (captcha.checkCode(code)) {
            this.clearCheckedCaptcha(captcha);
            return true;
        }
        return false;
    }

    /*@Override
    public boolean checkCaptcha(Captcha source) {
        var token = source.getToken();
        var code = source.getCode();
        var captcha = this.captchaRepository.findByToken(token)
                .orElseThrow(() -> new CaptchaException("Invalid captcha"));
        if (captcha.checkCode(code)) {
            this.clearCheckedCaptcha(captcha);
            return true;
        }
        return false;
    }*/

    protected abstract void doClearCaptcha(Captcha captcha) throws CaptchaException;

    protected abstract void doGenerateCaptcha(Captcha captcha) throws CaptchaException;

    protected String createToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    protected String generateCode() {
        return ThreadLocalRandom.current().ints(this.codeLength, 0, 10)
                .mapToObj(Integer::toString).collect(Collectors.joining());
    }
}
