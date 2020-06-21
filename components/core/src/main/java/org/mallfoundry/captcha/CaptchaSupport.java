package org.mallfoundry.captcha;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class CaptchaSupport implements Captcha {

    @Override
    public boolean checkCode(String code) throws CaptchaException {
        var time = System.currentTimeMillis() - this.getCreatedTime().getTime();
        if (this.getExpires() < time) {
            throw new CaptchaException("The captcha has expired");
        }
        return Objects.equals(code, this.getCode());
    }

    @Override
    public String getParameter(String name) {
        return this.getParameters().get(name);
    }
}
