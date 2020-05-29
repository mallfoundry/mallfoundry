package org.mallfoundry.rest.captcha;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.captcha.CaptchaType;

@Getter
@Setter
public class CaptchaRequest {
    private CaptchaType type;
}
