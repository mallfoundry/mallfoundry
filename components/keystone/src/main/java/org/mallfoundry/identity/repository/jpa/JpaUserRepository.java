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

package org.mallfoundry.identity.repository.jpa;

import org.mallfoundry.identity.User;
import org.mallfoundry.identity.UserId;
import org.mallfoundry.identity.UserRepository;
import org.mallfoundry.identity.UserSearch;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    private final JpaUserRepositoryDelegate repository;

    public JpaUserRepository(JpaUserRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public User create(UserId id) {
        return new JpaUser(id);
    }

    @Override
    public User save(User user) {
        return this.repository.save(JpaUser.of(user));
    }

    @Override
    public void delete(User user) {
        this.repository.delete(JpaUser.of(user));
    }

    @Override
    public Optional<User> findById(UserId id) {
        return CastUtils.cast(this.repository.findById(id.getId()));
    }

    @Override
    public Optional<User> findBySearch(UserSearch search) {
        if (Objects.nonNull(search.getUsername())) {
            return CastUtils.cast(this.repository.findByUsername(search.getUsername()));
        } else if (Objects.nonNull(search.getCountryCode()) && Objects.nonNull(search.getPhone())) {
            return CastUtils.cast(this.repository.findByCountryCodeAndPhone(search.getCountryCode(), search.getPhone()));
        }
        return Optional.empty();
    }
}
