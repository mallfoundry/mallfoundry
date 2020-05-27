package org.mallfoundry.identity;

public class InternalUserChangedEvent extends UserEventSupport implements UserChangedEvent {

    public InternalUserChangedEvent(User user) {
        super(user);
    }
}
