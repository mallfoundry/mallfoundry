/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
