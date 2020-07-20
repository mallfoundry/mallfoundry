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

import org.mallfoundry.security.acl.AccessControl;
import org.mallfoundry.security.acl.AccessControlRepository;
import org.mallfoundry.security.acl.Principal;
import org.mallfoundry.security.acl.Resource;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class JpaAccessControlRepository implements AccessControlRepository {

    private final JpaAccessControlRepositoryDelegate repository;

    public JpaAccessControlRepository(JpaAccessControlRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public AccessControl create(String id) {
        return new JpaAccessControl(id);
    }

    @Override
    public Optional<AccessControl> findByResource(Resource resource) {
        return CastUtils.cast(repository.findByResource(resource));
    }

    @Override
    public Optional<AccessControl> findByResourceAndPrincipals(Resource resource, Set<Principal> principals) {
        return CastUtils.cast(this.repository.findByResourceAndPrincipals(resource, principals));
    }

    @Override
    public AccessControl save(AccessControl control) {
        return this.repository.save(JpaAccessControl.of(control));
    }
}
