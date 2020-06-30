package org.mallfoundry.identity;

public class ImmutableUserCredentialsChangedEvent extends UserEventSupport implements UserCredentialsChangedEvent {
    public ImmutableUserCredentialsChangedEvent(User user) {
        super(user);
    }
}
