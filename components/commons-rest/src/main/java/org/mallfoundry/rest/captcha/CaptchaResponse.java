package org.mallfoundry.rest.captcha;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.captcha.Captcha;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class CaptchaResponse extends CaptchaRequest {

    private String token;

    private int expires;

    private int intervals;

    private Date createdTime;

    public CaptchaResponse(Captcha captcha) {
        this.setType(captcha.getType());
        this.setParameters(Map.copyOf(captcha.getParameters()));
        this.setToken(captcha.getToken());
        this.setExpires(captcha.getExpires());
        this.setIntervals(captcha.getIntervals());
        this.setCreatedTime(captcha.getCreatedTime());
    }
}
