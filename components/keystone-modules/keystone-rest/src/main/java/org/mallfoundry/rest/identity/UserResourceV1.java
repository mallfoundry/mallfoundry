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

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.identity.UserSearch;
import org.mallfoundry.identity.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Users")
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

    @PatchMapping("/users/retake")
    public UserResponse retakeUser(@RequestBody UserRetakeRequest request) {
        return UserResponse.of(this.userService.retakeUser(request));
    }

    @GetMapping("/users/{id}")
    public Optional<UserResponse> findUser(@PathVariable("id") String id) {
        return this.userService.findUser(this.userService.createUserId(id)).map(UserResponse::of);
    }

    @GetMapping("/users/find")
    public Optional<UserResponse> findUser(@RequestParam(name = "username", required = false) String username,
                                           @RequestParam(name = "country_code", required = false) String countryCode,
                                           @RequestParam(name = "phone", required = false) String phone,
                                           @RequestParam(name = "email", required = false) String email) {
        return this.userService
                .findUser(new UserSearch() {
                    @Override
                    public String getUsername() {
                        return username;
                    }

                    @Override
                    public String getCountryCode() {
                        return countryCode;
                    }

                    @Override
                    public String getPhone() {
                        return phone;
                    }

                    @Override
                    public String getEmail() {
                        return email;
                    }
                })
                .map(UserResponse::of);
    }

    @GetMapping("/users/current")
    public UserResponse getCurrentUser() {
        return UserResponse.of(this.userService.getCurrentUser());
    }

    @PatchMapping("/users/{id}")
    public void updateUser(@PathVariable("id") String id, @RequestBody UserRequest request) {
        this.userService.updateUser(request.assignTo(this.userService.createUser(this.userService.createUserId(id))));
    }
}
