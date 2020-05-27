package org.mallfoundry.identity;

public class InternalUserCreatedEvent extends UserEventSupport implements UserChangedEvent {

    public InternalUserCreatedEvent(User user) {
        super(user);
    }
}
