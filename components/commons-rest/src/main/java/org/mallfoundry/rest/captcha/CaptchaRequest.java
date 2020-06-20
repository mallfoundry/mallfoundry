package org.mallfoundry.rest.captcha;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaType;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CaptchaRequest {
    private CaptchaType type;
    private Map<String, String> parameters = new HashMap<>();

    public Captcha assignToCaptcha(Captcha captcha) {
        return captcha.toBuilder().parameters(parameters).build();
    }
}
