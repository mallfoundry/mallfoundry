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

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    public AccessTokenService(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public AccessToken createToken(String username) {
        return AccessToken.newToken(username, extractTokenValue(username));
    }

    @CacheEvict(value = "AccessTokens", key = "#token.token")
    public AccessToken storeAccessToken(AccessToken token) {
        return this.accessTokenRepository.save(token);
    }

    @CacheEvict(value = "AccessTokens", key = "#tokenValue")
    public void deleteAccessToken(String tokenValue) {
        this.accessTokenRepository.deleteByToken(tokenValue);
    }

    @Cacheable(value = "AccessTokens", key = "#tokenValue")
    public Optional<AccessToken> readAccessToken(String tokenValue) {
        return this.accessTokenRepository.findByToken(tokenValue);
    }

    public Optional<AccessToken> getAccessToken(String username) {
        return this.accessTokenRepository.findByUsername(username);
    }

    private String extractTokenValue(String value) {
        return DigestUtils.md5Hex(value);
    }
}
