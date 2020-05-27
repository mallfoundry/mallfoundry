package org.mallfoundry.identity;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public abstract class UserEventSupport extends ApplicationEvent implements UserEvent {

    @Getter
    private final User user;

    public UserEventSupport(User user) {
        super(user);
        this.user = user;
    }
}
