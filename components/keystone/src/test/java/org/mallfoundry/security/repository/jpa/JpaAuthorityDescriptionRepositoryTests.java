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

import org.junit.jupiter.api.Test;
import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@StandaloneTest
public class JpaAuthorityDescriptionRepositoryTests {

    @Autowired
    private JpaAuthorityDescriptionRepository repository;

    @Transactional
    @Rollback(false)
    @Test
    public void testSaveAuthorities() {
        var storeDescription = new JpaAuthorityDescription().toBuilder()
                .name("店铺管理").authority(AllAuthorities.STORE_MANAGE)
                .addAuthority(new JpaAuthorityDescription().toBuilder()
                        .name("角色").authority(AllAuthorities.STORE_ROLE_MANAGE)
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("查看").authority(AllAuthorities.STORE_ROLE_READ).build())
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("编辑").authority(AllAuthorities.STORE_ROLE_WRITE).build())
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("删除").authority(AllAuthorities.STORE_ROLE_DELETE).build())
                        .build())
                .addAuthority(new JpaAuthorityDescription().toBuilder()
                        .name("员工").authority(AllAuthorities.STORE_STAFF_MANAGE)
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("查看").authority(AllAuthorities.STORE_STAFF_READ).build())
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("编辑").authority(AllAuthorities.STORE_STAFF_WRITE).build())
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("删除").authority(AllAuthorities.STORE_STAFF_DELETE).build())
                        .build())
                .addAuthority(new JpaAuthorityDescription().toBuilder()
                        .name("客户").authority(AllAuthorities.STORE_MEMBER_MANAGE)
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("查看").authority(AllAuthorities.STORE_MEMBER_READ).build())
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("编辑").authority(AllAuthorities.STORE_MEMBER_WRITE).build())
                        .addAuthority(new JpaAuthorityDescription().toBuilder().name("删除").authority(AllAuthorities.STORE_MEMBER_DELETE).build())
                        .build())
                .build();
        this.repository.save(storeDescription);
    }
}
