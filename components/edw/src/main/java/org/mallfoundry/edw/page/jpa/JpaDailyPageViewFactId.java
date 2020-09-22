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

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class JpaDailyPageViewFactId implements Serializable {

    private String pageId;

    private String pageTypeId;

    private String browserId;

    private String browserIpId;

    private int dateId;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaDailyPageViewFactId)) {
            return false;
        }
        JpaDailyPageViewFactId that = (JpaDailyPageViewFactId) object;
        return dateId == that.dateId
                && Objects.equals(pageId, that.pageId)
                && Objects.equals(pageTypeId, that.pageTypeId)
                && Objects.equals(browserId, that.browserId)
                && Objects.equals(browserIpId, that.browserIpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageId, pageTypeId, browserId, browserIpId, dateId);
    }
}
