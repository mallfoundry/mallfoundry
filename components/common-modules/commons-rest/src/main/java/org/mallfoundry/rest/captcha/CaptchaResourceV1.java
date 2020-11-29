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

package org.mallfoundry.rest.captcha;


import org.mallfoundry.captcha.CaptchaService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@RestController
public class CaptchaResourceV1 {

    private final CaptchaService captchaService;

    public CaptchaResourceV1(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @PostMapping("/captchas")
    public CaptchaResponse generateCaptcha(@RequestBody CaptchaRequest request) {
        var captcha = request.assignToCaptcha(
                this.captchaService.createCaptcha(request.getType()));
        return new CaptchaResponse(this.captchaService.generateCaptcha(captcha));
    }

    /*@PostMapping("/captchas/{token}/check")
    public boolean checkCaptcha(@PathVariable("token") String token, @RequestBody String code) {
        return this.captchaService.checkCaptcha(token, code);
    }*/
}
