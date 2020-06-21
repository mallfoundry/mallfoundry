package org.mallfoundry.captcha;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public enum CaptchaType {
    SMS;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
