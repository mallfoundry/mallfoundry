package org.mallfoundry.sms;

public abstract class AbstractMessageService implements MessageService {

    @Override
    public Message createMessage() {
        return new DefaultMessage();
    }
}
