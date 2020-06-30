package org.mallfoundry.identity.validation;

import org.mallfoundry.identity.UserRegistration;
import org.mallfoundry.identity.UserRepository;
import org.mallfoundry.identity.UserValidator;
import org.mallfoundry.identity.UserValidatorException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static org.mallfoundry.i18n.MessageHolder.message;

@Component
public class RegistrationUserValidator implements UserValidator {

    private final UserRepository userRepository;

    public RegistrationUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validateCreateUser(UserRegistration registration) throws UserValidatorException {
        var mobile = registration.getMobile();
        if (Objects.nonNull(mobile)) {
            this.userRepository.findByMobile(mobile).ifPresent(user -> {
                throw new UserValidatorException(
                        message("identity.user.validation.mobileRegistered",
                                List.of(mobile),
                                "Mobile has been registered"));
            });
        }

    }
}
