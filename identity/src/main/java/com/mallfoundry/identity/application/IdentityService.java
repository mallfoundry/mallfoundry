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

package com.mallfoundry.identity.application;

import com.mallfoundry.identity.domain.User;

/**
 * A identity service.
 *
 * @author Zhi Tang
 */
public interface IdentityService {

    /**
     * Create a user.
     *
     * @param user a user
     */
    void createUser(User user);

    /**
     * Delete the user based on the username.
     *
     * @param username username
     */
    void deleteUser(String username);

    /**
     * Update user information based on username
     *
     * @param user a user
     */
    void updateUser(User user);

    User getUser(String username);
}
