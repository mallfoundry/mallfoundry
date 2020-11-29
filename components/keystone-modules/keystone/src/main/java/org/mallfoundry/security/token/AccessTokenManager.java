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

import org.mallfoundry.security.authentication.Credentials;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccessTokenManager {

    private final List<AccessTokenProvider> providers;

    private final AccessTokenService tokenService;

    public AccessTokenManager(List<AccessTokenProvider> providers, AccessTokenService tokenService) {
        this.providers = providers;
        this.tokenService = tokenService;
    }

    public AccessToken createAccessToken(Credentials credentials) throws AuthenticationException {
        var tokenId = this.getAccessTokenId(credentials);
        var username = tokenId.getUsername();
        return tokenService.getAccessToken(username)
                .orElseGet(() -> this.tokenService.storeAccessToken(this.tokenService.createToken(username)));
    }

    private AccessTokenId getAccessTokenId(Credentials credentials) {
        if (Objects.isNull(credentials)) {
            throw new AccessTokenException("Credentials cannot be null");
        }
        for (var provider : this.providers) {
            if (provider.supports(credentials)) {
                var tokenId = provider.authenticate(credentials);
                if (Objects.nonNull(tokenId)) {
                    return tokenId;
                }
            }
        }
        throw new AccessTokenException(String.format("This type of %s Credentials is not supported", credentials.getType()));
    }

    public void deleteAccessToken(String tokenValue) {
        this.tokenService.deleteAccessToken(tokenValue);
    }
}
