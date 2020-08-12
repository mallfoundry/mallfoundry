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

import org.mallfoundry.security.access.Principal;
import org.mallfoundry.security.access.PrincipalRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class DelegatingJpaPrincipalRepository implements PrincipalRepository {

    private final JpaPrincipalRepository principalRepository;

    private final JpaAccessControlEntryRepository controlEntryRepository;

    public DelegatingJpaPrincipalRepository(JpaPrincipalRepository principalRepository,
                                            JpaAccessControlEntryRepository controlEntryRepository) {
        this.principalRepository = principalRepository;
        this.controlEntryRepository = controlEntryRepository;
    }

    @Override
    public Principal create(String id) {
        return new JpaPrincipal(id);
    }

    @Override
    public Principal save(Principal principal) {
        return this.principalRepository.save(JpaPrincipal.of(principal));
    }

    @Override
    public List<Principal> saveAll(List<Principal> principals) {
        return CastUtils.cast(this.principalRepository.saveAll(CastUtils.cast(principals)));
    }

    @Override
    public Optional<Principal> findByTypeAndName(String type, String name) {
        return CastUtils.cast(this.principalRepository.findByTypeAndName(type, name));
    }

    @Override
    public List<Principal> findAllByPrincipals(Collection<Principal> principals) {
        return CastUtils.cast(this.principalRepository.findAll(principals));
    }

    @Override
    public void delete(Principal principal) {
        this.principalRepository.delete(JpaPrincipal.of(principal));
    }

    @Override
    public void deleteAll(List<Principal> principals) {
        List<JpaPrincipal> jpaPrincipals = this.principalRepository.findAll(principals);
        this.controlEntryRepository.deleteAllByPrincipalIn(CastUtils.cast(jpaPrincipals));
        this.principalRepository.deleteAll(jpaPrincipals);
    }
}
