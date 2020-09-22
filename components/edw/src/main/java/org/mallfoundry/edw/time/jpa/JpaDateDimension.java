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
import org.mallfoundry.edw.time.DateDimension;
import org.mallfoundry.edw.time.DateDimensions;
import org.mallfoundry.edw.time.Quarters;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_edw_date_dimension")
public class JpaDateDimension implements DateDimension {

    @Id
    @Column(name = "id_")
    private int id;

    @Column(name = "date_")
    private int date;

    @Column(name = "year_")
    private int year;

    @Column(name = "month_")
    private int month;

    @Column(name = "year_month_")
    private int yearMonth;

    @Column(name = "quarter_")
    private int quarter;

    @Column(name = "day_of_month_")
    private int dayOfMonth;

    public JpaDateDimension(Date date) {
        this.id = DateDimensions.idOf(date);
        this.date = this.id;
        var localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        this.year = localDate.getYear();
        this.month = localDate.getMonthValue();
        this.yearMonth = DateDimensions.yearMonthOf(date);
        this.quarter = Quarters.getQuarterOfMonth(this.month);
        this.dayOfMonth = localDate.getDayOfMonth();
    }

    public static JpaDateDimension of(DateDimension dateDimension) {
        if (dateDimension instanceof JpaDateDimension) {
            return (JpaDateDimension) dateDimension;
        }
        var target = new JpaDateDimension();
        BeanUtils.copyProperties(dateDimension, target);
        return target;
    }
}
