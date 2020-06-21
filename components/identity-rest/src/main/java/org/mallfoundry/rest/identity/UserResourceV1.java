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

package org.mallfoundry.rest.identity;

import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.SecurityUserHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/v1")
@RestController
public class UserResourceV1 {

    private final UserService userService;

    public UserResourceV1(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserResponse createUser(@RequestBody UserCreateRequest request) {
        return UserResponse.of(this.userService.createUser(request));
    }

    @GetMapping("/users/{id}")
    public Optional<UserResponse> getUser(@PathVariable("id") String id) {
        return this.userService.getUser(id).map(UserResponse::of);
    }

    @GetMapping("/users/current")
    public Optional<UserResponse> getCurrentUser() {
        return this.userService.getUser(SecurityUserHolder.getUserId()).map(UserResponse::of);
    }

    @PatchMapping("/users/{id}")
    public void setUser(@PathVariable("id") String id, @RequestBody UserRequest request) {
        this.userService.setUser(request.assignToUser(this.userService.createUser(id)));
    }
}
