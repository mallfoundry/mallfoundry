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

package org.mallfoundry.edw.time.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.edw.time.TimeDimension;
import org.mallfoundry.edw.time.TimeDimensions;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_edw_time_dimension")
public class JpaTimeDimension implements TimeDimension {

    @Id
    @Column(name = "key_")
    private int key;

    @Column(name = "time_")
    private LocalTime time;

    @Column(name = "hour_")
    private byte hour;

    @Column(name = "minute_")
    private byte minute;

    @Column(name = "second_")
    private byte second;

    public JpaTimeDimension(Date date) {
        var time = Optional.ofNullable(date)
                .map(Date::toInstant)
                .map(instant -> instant.atZone(ZoneId.systemDefault()))
                .map(ZonedDateTime::toLocalTime)
                .orElseThrow();
        this.key = TimeDimensions.keyOf(time);
        this.time = time;
        this.hour = (byte) time.getHour();
        this.minute = (byte) time.getMinute();
        this.second = (byte) time.getSecond();
    }

    public static JpaTimeDimension of(TimeDimension timeDimension) {
        if (timeDimension instanceof JpaTimeDimension) {
            return (JpaTimeDimension) timeDimension;
        }
        var target = new JpaTimeDimension();
        BeanUtils.copyProperties(timeDimension, target);
        return target;
    }
}
