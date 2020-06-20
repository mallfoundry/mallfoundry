package org.mallfoundry.captcha.repository.redis;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.captcha.CaptchaSupport;
import org.mallfoundry.captcha.CaptchaType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RedisCaptcha extends CaptchaSupport {

    private CaptchaType type;

    private String token;

    private String code;

    private Map<String, String> parameters = new HashMap<>();

    private int expires;

    private Date createdTime;
}
