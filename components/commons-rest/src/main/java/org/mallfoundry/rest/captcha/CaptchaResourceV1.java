package org.mallfoundry.rest.captcha;


import org.mallfoundry.captcha.Captcha;
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
    public Captcha generateCaptcha(@RequestBody CaptchaRequest request) {
        return this.captchaService.generateCaptcha(request.getType());
    }

    @PostMapping("/captchas/{token}/check")
    public boolean checkCaptcha(@PathVariable("token") String token, @RequestBody String code) {
        return this.captchaService.checkCaptcha(token, code);
    }
}
