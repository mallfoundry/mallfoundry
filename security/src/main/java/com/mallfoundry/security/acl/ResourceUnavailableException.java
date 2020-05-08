package com.mallfoundry.security.acl;

public class ResourceUnavailableException extends RuntimeException {

    public ResourceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
