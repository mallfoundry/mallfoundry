package org.mallfoundry.captcha.repository.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaSupport;
import org.mallfoundry.captcha.CaptchaType;
import org.mallfoundry.data.jpa.convert.StringStringMapConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_captcha")
public class JpaCaptcha extends CaptchaSupport {

    private CaptchaType type;

    private String token;

    private String code;

    private Map<String, String> parameters = new HashMap<>();

    private int expires;

    private int intervals;

    private Date createdTime;

    @JsonIgnore
    @Column(name = "mobile_", length = 20)
    public String getMobile() {
        return this.getParameters().getOrDefault("mobile", null);
    }

    public void setMobile(String mobile) {
        if (Objects.nonNull(mobile) && !this.getParameters().containsKey("mobile")) {
            this.getParameters().put("mobile", mobile);
        }
    }

    @Column(name = "type_")
    @Override
    public CaptchaType getType() {
        return type;
    }

    @Id
    @Column(name = "token_")
    @Override
    public String getToken() {
        return token;
    }

    @Column(name = "code_")
    @Override
    public String getCode() {
        return code;
    }

    @Convert(converter = StringStringMapConverter.class)
    @Column(name = "parameters_", length = 1024)
    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    @Column(name = "intervals_")
    @Override
    public int getIntervals() {
        return intervals;
    }

    @Column(name = "expires_")
    @Override
    public int getExpires() {
        return expires;
    }

    @Column(name = "created_time_")
    @Override
    public Date getCreatedTime() {
        return createdTime;
    }

    public JpaCaptcha(CaptchaType type) {
        this.setType(type);
        this.setCreatedTime(new Date());
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
