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

package org.mallfoundry.security.access;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class AuthoritiesOptimizeTests {

    @Test
    public void testOptimize() {
        var authorities = Set.of(
                AllAuthorities.STORE_ROLE_ADD,
                AllAuthorities.STORE_ROLE_WRITE,
                AllAuthorities.STORE_ROLE_READ,
                AllAuthorities.STORE_ROLE_DELETE,
                //
                AllAuthorities.STORE_STAFF_ADD,
                AllAuthorities.STORE_STAFF_WRITE,
                AllAuthorities.STORE_STAFF_READ,
                AllAuthorities.STORE_STAFF_DELETE
        );
        var optimizedAuthorities = AuthoritiesOptimizeUtils.optimizeAuthorities(authorities);
        Assertions.assertThat(optimizedAuthorities).containsOnly(AllAuthorities.STORE_ROLE_MANAGE);
    }
}
