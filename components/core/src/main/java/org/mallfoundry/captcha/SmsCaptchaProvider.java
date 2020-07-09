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
import org.mallfoundry.sms.Message;
import org.mallfoundry.sms.MessageService;

public class SmsCaptchaProvider implements CaptchaProvider {

    private final MessageService messageService;

    private final CaptchaRepository captchaRepository;

    @Setter
    private int expires;

    @Setter
    private int intervals;

    @Setter
    private String template;

    @Setter
    private String signature;

    public SmsCaptchaProvider(MessageService messageService,
                              CaptchaRepository captchaRepository) {
        this.messageService = messageService;
        this.captchaRepository = captchaRepository;
    }

    @Override
    public void clearCaptcha(Captcha captcha) throws CaptchaException {
        var mobile = captcha.getParameters().get("mobile");
        this.captchaRepository.deleteByMobile(mobile);
    }

    private void setCaptchaIntervals(Captcha captcha) {
        if (this.expires != 0) {
            captcha.setExpires(this.expires);
        }
        if (this.intervals != 0) {
            captcha.setIntervals(this.intervals);
        }
    }

    @Override
    public void generateCaptcha(Captcha captcha) throws CaptchaException {
        var mobile = captcha.getParameters().get("mobile");
        this.setCaptchaIntervals(captcha);
        this.sendMessage(mobile, captcha.getCode());
    }

    private Message createMessage(String mobile, String code) {
        return this.messageService.createMessage().toBuilder()
                .mobile(mobile).signature(this.signature)
                .template(this.template).variable(Message.CODE_VARIABLE_NAME, code)
                .build();
    }

    private void sendMessage(String mobile, String code) {
        this.messageService.sendMessage(this.createMessage(mobile, code));
    }

    @Override
    public boolean supports(Captcha captcha) {
        return captcha.getType() == CaptchaType.SMS;
    }
}
