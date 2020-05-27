package org.mallfoundry.identity;

public interface UserValidator {

    void validateCreateUser(UserRegistration registration) throws UserValidatorException;
}
