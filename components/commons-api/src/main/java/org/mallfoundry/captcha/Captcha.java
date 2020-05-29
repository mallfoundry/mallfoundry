package org.mallfoundry.captcha;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.Map;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Captcha {

    String MOBILE_PARAMETER_NAME = "mobile";

    CaptchaType getType();

    String getToken();

    void setToken(String token);

    String getCode();

    void setCode(String code);

    Map<String, String> getParameters();

    String getParameter(String name);

    void setParameters(Map<String, String> parameters);

    int getExpires();

    void setExpires(int expires);

    Date getCreatedTime();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Captcha> {
        
        Builder token(String token);

        Builder code(String code);
    }

    class BuilderSupport implements Builder {

        private final Captcha captcha;

        public BuilderSupport(Captcha captcha) {
            this.captcha = captcha;
        }

        @Override
        public Builder token(String token) {
            this.captcha.setToken(token);
            return this;
        }

        @Override
        public Builder code(String code) {
            this.captcha.setCode(code);
            return this;
        }

        @Override
        public Captcha build() {
            return this.captcha;
        }
    }

}
