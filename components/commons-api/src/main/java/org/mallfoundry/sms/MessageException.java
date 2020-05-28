package org.mallfoundry.sms;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public class MessageException extends RuntimeException {

    public MessageException(String message) {
        super(message);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
