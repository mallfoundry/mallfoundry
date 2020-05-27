package org.mallfoundry.captcha.repository.jpa;

import lombok.NoArgsConstructor;
import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaSupport;
import org.mallfoundry.captcha.CaptchaType;
import org.mallfoundry.data.jpa.convert.StringStringMapConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "captchas")
public class JpaCaptcha extends CaptchaSupport {

    @Column(name = "type_")
    @Override
    public CaptchaType getType() {
        return super.getType();
    }

    @Id
    @Column(name = "token_")
    @Override
    public String getToken() {
        return super.getToken();
    }

    @Column(name = "code_")
    @Override
    public String getCode() {
        return super.getCode();
    }

    @Convert(converter = StringStringMapConverter.class)
    @Column(name = "parameters_", length = 1024)
    @Override
    public Map<String, String> getParameters() {
        return super.getParameters();
    }

    @Column(name = "mobile_", length = 20)
    public String getMobile() {
        return this.getParameters().getOrDefault("mobile", null);
    }

    public void setMobile(String mobile) {
        if (Objects.nonNull(mobile)
                && !super.getParameters().containsKey("mobile")) {
            this.getParameters().put("mobile", mobile);
        }
    }

    public JpaCaptcha(CaptchaType type) {
        this.setType(type);
    }

    public static JpaCaptcha of(Captcha captcha) {
        if (captcha instanceof JpaCaptcha) {
            return (JpaCaptcha) captcha;
        }
        return (JpaCaptcha) new JpaCaptcha(captcha.getType())
                .toBuilder()
                .code(captcha.getCode())
                .token(captcha.getToken())
                .build();
    }
}
