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

package org.mallfoundry.security.access.repository.jpa;

import org.mallfoundry.security.access.MutablePrincipal;
import org.mallfoundry.security.access.Principal;
import org.mallfoundry.security.access.PrincipalRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaPrincipalRepository implements PrincipalRepository {

    private final JpaPrincipalRepositoryDelegate repository;

    public JpaPrincipalRepository(JpaPrincipalRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public MutablePrincipal create(String id) {
        return new JpaPrincipal(id);
    }

    @Override
    public Principal save(Principal principal) {
        return this.repository.save(JpaPrincipal.of(principal));
    }

    @Override
    public Optional<Principal> findByTypeAndName(String type, String name) {
        return CastUtils.cast(this.repository.findByTypeAndName(type, name));
    }
}
