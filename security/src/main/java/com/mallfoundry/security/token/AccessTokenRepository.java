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

package com.mallfoundry.security.token;

import java.util.Optional;

public interface AccessTokenRepository {

    AccessToken save(AccessToken token);

    void deleteByUsername(String username);

    void deleteByToken(String tokenValue);

    Optional<AccessToken> findByToken(String tokenValue);

    Optional<AccessToken> findByUsername(String username);
}
