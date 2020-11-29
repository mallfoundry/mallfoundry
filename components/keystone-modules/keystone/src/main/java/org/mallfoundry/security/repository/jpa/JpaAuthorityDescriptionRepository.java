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

import org.mallfoundry.security.AuthorityDescription;
import org.mallfoundry.security.AuthorityDescriptionId;
import org.mallfoundry.security.AuthorityDescriptionRepository;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaAuthorityDescriptionRepository implements AuthorityDescriptionRepository {

    private final DelegatingJpaAuthorityDescriptionRepository repository;

    public JpaAuthorityDescriptionRepository(DelegatingJpaAuthorityDescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthorityDescription create(String authority, String language) {
        return new JpaAuthorityDescription(language, authority);
    }

    @Override
    public AuthorityDescription save(AuthorityDescription authority) {
        return this.repository.save(JpaAuthorityDescription.of(authority));
    }

    @Override
    public List<AuthorityDescription> saveAll(List<AuthorityDescription> descriptions) {
        var jpaDescriptions = descriptions.stream().map(JpaAuthorityDescription::of)
                .collect(Collectors.toUnmodifiableList());
        return CastUtils.cast(this.repository.saveAll(jpaDescriptions));
    }

    @Override
    public Optional<AuthorityDescription> findById(AuthorityDescriptionId id) {
        return CastUtils.cast(this.repository.findById(id.getAuthority()));
    }
}
