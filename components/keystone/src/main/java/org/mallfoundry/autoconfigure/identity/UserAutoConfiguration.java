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

package org.mallfoundry.autoconfigure.identity;


import org.mallfoundry.captcha.CaptchaService;
import org.mallfoundry.config.UserConfigurationIdRetrievalStrategy;
import org.mallfoundry.identity.DefaultUserService;
import org.mallfoundry.identity.UserProcessor;
import org.mallfoundry.identity.UserRepository;
import org.mallfoundry.identity.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@EnableConfigurationProperties(UserProperties.class)
@Configuration
public class UserAutoConfiguration {

    @Bean
    public UserConfigurationIdRetrievalStrategy userConfigurationIdRetrievalStrategy() {
        return new UserConfigurationIdRetrievalStrategy();
    }

    @Bean
    public DefaultUserService defaultUserService(@Autowired(required = false)
                                                 @Lazy List<UserProcessor> processors,
                                                 @Lazy List<UserValidator> userValidators,
                                                 CaptchaService captchaService,
                                                 UserRepository userRepository) {
        var service = new DefaultUserService(userValidators, captchaService, userRepository);
        service.setProcessors(processors);
        return service;
    }
}
