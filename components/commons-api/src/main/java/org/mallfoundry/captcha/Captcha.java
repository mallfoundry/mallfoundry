package org.mallfoundry.captcha;

public interface Captcha {

    String getToken();

    void setToken(String token);

    String getCode();

    void setCode(String code);

    CaptchaType getType();
}
