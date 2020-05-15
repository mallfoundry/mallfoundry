package com.mallfoundry.identity;

import org.springframework.context.ApplicationEvent;

public class InternalUserDeletedEvent extends ApplicationEvent {

    public InternalUserDeletedEvent(User user) {
        super(user);
    }
}
