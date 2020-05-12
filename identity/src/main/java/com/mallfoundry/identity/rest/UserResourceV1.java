/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.identity.rest;

import com.mallfoundry.identity.InternalUser;
import com.mallfoundry.identity.InternalUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/v1")
@RestController
public class UserResourceV1 {

    private final InternalUserService userService;

    public UserResourceV1(InternalUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{username}")
    public Optional<InternalUser> getUser(@PathVariable("username") String username) {
        return this.userService.getUser(username);
    }
}
