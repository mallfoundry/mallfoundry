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
import lombok.Setter;
import org.mallfoundry.edw.time.TimeDimension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "mf_edw_time_dimension")
public class JpaTimeDimension implements TimeDimension {

    @Id
    @Column(name = "key_")
    private long key;

    @Column(name = "year_")
    private int year;

    @Column(name = "quarter_")
    private int quarter;

    @Column(name = "month_")
    private int month;

    @Column(name = "day_of_month_")
    private int dayOfMonth;

    @Column(name = "hour_of_day_")
    private int hourOfDay;

    @Column(name = "minute_of_hour_")
    private int minuteOfHour;

    @Column(name = "second_of_minute_")
    private int secondOfMinute;
}
