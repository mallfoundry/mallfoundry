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

package org.mallfoundry.store.staff;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mallfoundry.security.test.WithSystemUser;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

@StandaloneTest
public class StaffServiceTests {

    @Autowired
    private StaffService staffService;

    @WithSystemUser
    @Test
    public void testAddStaff() {
        var staff = this.staffService.createStaff("");
        staff.setStoreId("mi");
        staff.setName("我爱工作");
        var savedStaff = this.staffService.addStaff(staff);
        Assertions.assertThat(savedStaff.getId()).is(new Condition<>(StringUtils::isNotBlank, "1"));
    }
}
