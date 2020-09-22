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

package org.mallfoundry.edw.page.jpa;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.edw.page.HourlyPageViewFact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "mf_edw_hourly_page_view_fact")
@IdClass(JpaHourlyPageViewFactId.class)
public class JpaHourlyPageViewFact implements HourlyPageViewFact {

    @Id
    @Column(name = "page_id_")
    private String pageId;

    @Id
    @Column(name = "page_type_id_")
    private String pageTypeId;

    @Id
    @Column(name = "browser_id_")
    private String browserId;

    @Id
    @Column(name = "browser_ip_id_")
    private String browserIpId;

    @Id
    @Column(name = "hourly_time_id_")
    private int hourlyTimeId;

    @Column(name = "view_count_")
    private int viewCount;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaHourlyPageViewFact)) {
            return false;
        }
        JpaHourlyPageViewFact that = (JpaHourlyPageViewFact) object;
        return hourlyTimeId == that.hourlyTimeId
                && Objects.equals(pageId, that.pageId)
                && Objects.equals(pageTypeId, that.pageTypeId)
                && Objects.equals(browserId, that.browserId)
                && Objects.equals(browserIpId, that.browserIpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageId, pageTypeId, browserId, browserIpId, hourlyTimeId);
    }
}
