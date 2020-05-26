package org.mallfoundry.rest.captcha;


import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaService;
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
    public Captcha createCaptcha(@RequestBody CaptchaRequest request) {
        return this.captchaService.createCaptcha(request.getType());
    }

//    @PostMapping("/captchas/{token}/check")
//    public Captcha checkCaptcha(@RequestBody CaptchaRequest request) {
//        return this.captchaService.checkCaptcha(request.getType());
//    }
}
