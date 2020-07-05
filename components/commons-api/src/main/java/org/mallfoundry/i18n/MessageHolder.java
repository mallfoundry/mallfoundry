package org.mallfoundry.i18n;

import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;

import static org.mallfoundry.i18n.Messages.codeKey;

public abstract class MessageHolder {

    private static MessageSourceAccessor messages;

    public static void setMessages(MessageSourceAccessor messages) {
        MessageHolder.messages = messages;
    }

    public static String message(String code) {
        return messages.getMessage(codeKey(code));
    }

    public static String message(String code, String defaultMessage) {
        return messages.getMessage(codeKey(code), defaultMessage);
    }

    public static String message(String code, List<?> args) {
        return messages.getMessage(codeKey(code), args.toArray());
    }

    public static String message(String code, List<?> args, String defaultMessage) {
        return messages.getMessage(codeKey(code), args.toArray(), defaultMessage);
    }
}
