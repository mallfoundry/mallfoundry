package org.mallfoundry.autoconfigure.i18n;

import org.mallfoundry.i18n.MessageHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
public class MessageAutoConfiguration implements MessageSourceAware {

    @Override
    public void setMessageSource(MessageSource messageSource) {
        MessageHolder.setMessages(new MessageSourceAccessor(messageSource));
    }
}
