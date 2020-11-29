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
import java.time.LocalDateTime;
import java.time.ZoneId;

@StandaloneTest
public class DefaultTimeDimensionManagerTests {

    @Autowired
    private TimeDimensionManager timeDimensionManager;

    @Test
    public void testSaveFrom00000To235959() {
        var startTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        var endTime = LocalDateTime.of(2020, 1, 2, 0, 0, 0);
        var i = 0;
        while (startTime.isBefore(endTime)) {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = startTime.atZone(zone).toInstant();
            var dateDimension = this.timeDimensionManager.createTimeDimension(Date.from(instant));
            this.timeDimensionManager.saveTimeDimension(dateDimension);
            startTime = startTime.plusSeconds(1);
        }
    }
}
