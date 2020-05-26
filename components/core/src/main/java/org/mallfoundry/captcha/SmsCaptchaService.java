package org.mallfoundry.captcha;


import lombok.Setter;
import org.mallfoundry.sms.Message;
import org.mallfoundry.sms.MessageService;

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
    protected void doGenerateCaptcha(Captcha captcha) {
        var mobile = captcha.getParameters().get("mobile");
        this.messageService.sendMessage(this.messageService.createMessage().toBuilder().mobile(mobile)
                .signature(this.signature)
                .template(this.template)
                .variable(Message.CODE_VARIABLE_NAME, captcha.getCode()).build());
    }
}
