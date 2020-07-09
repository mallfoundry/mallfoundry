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

package org.mallfoundry.security.token;

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
