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

package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.MutableResource;
import org.mallfoundry.security.acl.ResourceRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaResourceRepository implements ResourceRepository {

    private final JpaResourceRepositoryDelegate repository;

    public JpaResourceRepository(JpaResourceRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public MutableResource create(String id) {
        return new JpaResource(id);
    }

    @Override
    public MutableResource create(String id, Object resource) {
        return new JpaResource(id, resource);
    }

    @Override
    public MutableResource create(String id, String identifier, String type) {
        return new JpaResource(id, identifier, type);
    }

    @Override
    public MutableResource save(MutableResource resource) {
        return this.repository.save(JpaResource.of(resource));
    }

    @Override
    public Optional<MutableResource> findByTypeAndIdentifier(String type, String identifier) {
        return CastUtils.cast(this.repository.findByTypeAndIdentifier(type, identifier));
    }
}
