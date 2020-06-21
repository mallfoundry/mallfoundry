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

//        this.sendMessage(mobile, captcha.getCode());
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
