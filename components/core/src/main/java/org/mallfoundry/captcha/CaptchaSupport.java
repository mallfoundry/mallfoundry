package org.mallfoundry.captcha;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class CaptchaSupport implements Captcha {

    @Override
    public boolean checkCode(String code) {
        return (this.getCreatedTime().getTime() + this.getExpires()) < System.currentTimeMillis()
                && Objects.equals(code, this.getCode());
    }

    @Override
    public String getParameter(String name) {
        return this.getParameters().get(name);
    }
}
