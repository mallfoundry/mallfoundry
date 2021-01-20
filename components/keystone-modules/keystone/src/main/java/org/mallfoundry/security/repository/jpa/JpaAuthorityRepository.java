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

package org.mallfoundry.security.repository.jpa;

import org.mallfoundry.security.Authority;
import org.mallfoundry.security.AuthorityId;
import org.mallfoundry.security.AuthorityRepository;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaAuthorityRepository implements AuthorityRepository {

    private final DelegatingJpaAuthorityRepository repository;

    public JpaAuthorityRepository(DelegatingJpaAuthorityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authority create(AuthorityId authorityId) {
        return new JpaAuthority(authorityId);
    }

    @Override
    public Authority save(Authority authority) {
        return this.repository.save(JpaAuthority.of(authority));
    }

    @Override
    public List<Authority> saveAll(List<Authority> descriptions) {
        var jpaDescriptions = descriptions.stream().map(JpaAuthority::of)
                .collect(Collectors.toUnmodifiableList());
        return CastUtils.cast(this.repository.saveAll(jpaDescriptions));
    }

    @Override
    public Optional<Authority> findById(AuthorityId id) {
        return CastUtils.cast(this.repository.findById(id.getCode()));
    }
}
