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

package org.mallfoundry.security.token.repository.jpa;

import org.mallfoundry.security.token.AccessToken;
import org.mallfoundry.security.token.AccessTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAccessTokenRepository
        extends AccessTokenRepository,
        JpaRepository<AccessToken, String> {

    @Modifying
    @Query("delete from AccessToken where username = ?1")
    @Override
    void deleteByUsername(String username);

    @Modifying
    @Query("delete from AccessToken where token = ?1")
    @Override
    void deleteByToken(String tokenValue);

    @Query("from AccessToken where token = ?1")
    @Override
    Optional<AccessToken> findByToken(String tokenValue);

    @Query("from AccessToken where username = ?1")
    @Override
    Optional<AccessToken> findByUsername(String username);
}
