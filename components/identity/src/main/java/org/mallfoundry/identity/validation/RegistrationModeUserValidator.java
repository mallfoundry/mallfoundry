package org.mallfoundry.identity.validation;

import org.mallfoundry.identity.UserRegistration;
import org.mallfoundry.identity.UserValidator;
import org.mallfoundry.identity.UserValidatorException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.mallfoundry.i18n.MessageHolder.message;

@Order
@Component
public class RegistrationModeUserValidator implements UserValidator {

    @Override
    public void validateCreateUser(UserRegistration registration) throws UserValidatorException {
        if (Objects.isNull(registration.getMode())) {
            throw new UserValidatorException(
                    message("identity.user.validation.emptyRegistrationMode",
                            "Registration mode must not be null"));
        }
    }
}
