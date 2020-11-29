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

package org.mallfoundry.security;

import org.mallfoundry.i18n.MessageHolder;

import java.util.Objects;
import java.util.Optional;

public class DefaultAuthorityService implements AuthorityService {

    private final AuthorityDescriptionRepository authorityDescriptionRepository;

    public DefaultAuthorityService(AuthorityDescriptionRepository authorityDescriptionRepository) {
        this.authorityDescriptionRepository = authorityDescriptionRepository;
    }

    @Override
    public AuthorityDescriptionId createAuthorityDescriptionId(String authority) {
        return this.createAuthorityDescriptionId(authority, null);
    }

    @Override
    public AuthorityDescriptionId createAuthorityDescriptionId(String authority, String language) {
        return new ImmutableAuthorityDescriptionId(authority, Objects.requireNonNullElseGet(language, MessageHolder::getLanguage));
    }

    @Override
    public AuthorityDescription createAuthorityDescription(String authority) {
        return this.createAuthorityDescription(authority, null);
    }

    @Override
    public AuthorityDescription createAuthorityDescription(String authority, String language) {
        return this.authorityDescriptionRepository.create(authority, Objects.requireNonNullElseGet(language, MessageHolder::getLanguage));
    }

    @Override
    public Optional<AuthorityDescription> getAuthorityDescription(AuthorityDescriptionId descriptionId) {
        return this.authorityDescriptionRepository.findById(descriptionId);
    }
}
