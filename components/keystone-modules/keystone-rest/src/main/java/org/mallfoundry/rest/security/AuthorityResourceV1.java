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

package org.mallfoundry.rest.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.security.AuthorityDescription;
import org.mallfoundry.security.AuthorityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Authorities")
@RestController
@RequestMapping("/v1")
public class AuthorityResourceV1 {
    private final AuthorityService authorityService;

    public AuthorityResourceV1(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("/authorities/{authority}/description")
    public Optional<AuthorityDescription> getAuthorityDescription(@PathVariable("authority") String authority) {
        return this.authorityService.getAuthorityDescription(this.authorityService.createAuthorityDescriptionId(authority));
    }
}
