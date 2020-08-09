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

package org.mallfoundry.rest.identity;

import org.mallfoundry.identity.UserService;
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
        return this.userService.getCurrentUser().map(UserResponse::of);
    }

    @PatchMapping("/users/{id}")
    public void updateUser(@PathVariable("id") String id, @RequestBody UserRequest request) {
        this.userService.updateUser(request.assignTo(this.userService.createUser(id)));
    }
}
