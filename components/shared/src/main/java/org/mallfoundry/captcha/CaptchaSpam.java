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

package org.mallfoundry.captcha;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_captcha_spam")
public class CaptchaSpam {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "intervals_")
    private int intervals;

    @Column(name = "created_time_")
    private Date createdTime;

    public CaptchaSpam(String id, int intervals, Date createdTime) {
        this.id = id;
        this.intervals = intervals;
        this.createdTime = Objects.nonNull(createdTime)
                ? createdTime
                : new Date();
    }

    public boolean isSpam() {
        var time = System.currentTimeMillis() - this.createdTime.getTime();
        return time < this.getIntervals();
    }
}
