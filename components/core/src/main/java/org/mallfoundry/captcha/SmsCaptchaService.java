package org.mallfoundry.captcha;


import lombok.Setter;
import org.mallfoundry.sms.Message;
import org.mallfoundry.sms.MessageService;

import java.util.Optional;

public class SmsCaptchaService extends AbstractCaptchaService {

    private final MessageService messageService;

    @Setter
    private String template;

    @Setter
    private String signature;

    public SmsCaptchaService(CaptchaRepository repository, MessageService messageService) {
        super(repository);
        this.messageService = messageService;
    }

    @Override
    protected void doGenerateCaptcha(Captcha captcha) throws CaptchaException {
        var mobile = captcha.getParameters().get("mobile");
//        var existCaptcha = this.captchaRepository.findByMobile(mobile).orElse(null);

        if (this.captchaRepository.findByMobile(mobile).isPresent()) {
            throw new CaptchaException("");
        }

        this.messageService.sendMessage(this.messageService.createMessage().toBuilder().mobile(mobile)
                .signature(this.signature)
                .template(this.template)
                .variable(Message.CODE_VARIABLE_NAME, captcha.getCode()).build());
    }

    @Override
    public Optional<Captcha> getCaptcha(String token) {
        return this.captchaRepository.findByToken(token);
    }
}
