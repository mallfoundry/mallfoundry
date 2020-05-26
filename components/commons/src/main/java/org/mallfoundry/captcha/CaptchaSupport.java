package org.mallfoundry.captcha;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public abstract class CaptchaSupport implements Captcha {

    protected CaptchaType type;

    private String token;

    private String code;

    private Map<String, String> parameters;

    private int expires;

    private Date createdTime;
}
