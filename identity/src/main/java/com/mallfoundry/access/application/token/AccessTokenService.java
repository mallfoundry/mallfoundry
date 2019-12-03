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

package com.mallfoundry.access.application.token;

import com.mallfoundry.access.domain.token.AccessToken;
import com.mallfoundry.access.domain.token.AccessTokenRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    public AccessTokenService(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public AccessToken createToken(String username) {
        return AccessToken.newToken(username, extractTokenValue(username));
    }

    public void storeAccessToken(AccessToken token) {
        this.accessTokenRepository.add(token);
    }

    public void deleteAccessToken(String tokenValue) {
        this.accessTokenRepository.deleteByToken(tokenValue);
    }

    public AccessToken readAccessToken(String tokenValue) {
        return this.accessTokenRepository.findByToken(tokenValue);
    }

    public AccessToken getAccessToken(String username) {
        return this.accessTokenRepository.findByUsername(username);
    }

    private String extractTokenValue(String value) {
        return DigestUtils.md5Hex(value);
    }
}
