package org.mallfoundry.security.acl;

import org.mallfoundry.security.SecurityException;

public class AccessDeniedException extends SecurityException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }
}
