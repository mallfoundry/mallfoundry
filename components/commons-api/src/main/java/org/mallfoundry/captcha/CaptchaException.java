package org.mallfoundry.captcha;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public class CaptchaException extends RuntimeException {

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }
}
