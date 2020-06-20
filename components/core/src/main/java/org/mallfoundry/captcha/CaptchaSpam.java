package org.mallfoundry.captcha;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.util.ObjectBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_captcha_spam")
public class CaptchaSpam {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "expires_")
    private int expires;

    @Column(name = "created_time_")
    private Date createdTime;

    public CaptchaSpam(String id, int expires, Date createdTime) {
        this.id = id;
        this.expires = expires;
        this.createdTime = Objects.nonNull(createdTime)
                ? createdTime
                : new Date();
    }

    public boolean isExpired() {
        var time = System.currentTimeMillis() - this.createdTime.getTime();
        return time - expires > 0;
    }
}
