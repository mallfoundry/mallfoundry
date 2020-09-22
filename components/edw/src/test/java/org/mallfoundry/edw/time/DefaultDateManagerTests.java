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

package org.mallfoundry.edw.time;

import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@StandaloneTest
public class DefaultDateManagerTests {

    @Autowired
    private DateManager dateManager;

    @Test
    public void testSaveFrom20200101To20251231() {
        var startDate = LocalDate.of(2020, 1, 1);
        var endDate = LocalDate.of(2025, 12, 31);
        while (startDate.isBefore(endDate)) {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = startDate.atStartOfDay().atZone(zone).toInstant();
            var dateDimension = this.dateManager.createDateDimension(Date.from(instant));
            this.dateManager.saveDateDimension(dateDimension);
            startDate = startDate.plusDays(1);
        }
    }
}
