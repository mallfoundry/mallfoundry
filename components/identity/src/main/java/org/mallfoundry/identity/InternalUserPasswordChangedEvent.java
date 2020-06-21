package org.mallfoundry.identity;

public class InternalUserPasswordChangedEvent extends UserEventSupport implements UserPasswordChangedEvent {
    public InternalUserPasswordChangedEvent(User user) {
        super(user);
    }
}
