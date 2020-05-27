package org.mallfoundry.captcha;

public class CaptchaException extends RuntimeException {

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }
}
