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

package org.mallfoundry.page;

import org.mallfoundry.data.Query;
import org.mallfoundry.data.QueryBuilder;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;

public interface PageViewQuery extends Query, ObjectBuilder.ToBuilder<PageViewQuery.Builder> {

    String getPageId();

    void setPageId(String pageId);

    String getBrowserId();

    void setBrowserId(String browserId);

    String getBrowserIp();

    void setBrowserIp(String browserIp);

    Date getBrowsingTimeFrom();

    void setBrowsingTimeFrom(Date browsingTimeFrom);

    Date getBrowsingTimeTo();

    void setBrowsingTimeTo(Date browsingTimeTo);

    interface Builder extends QueryBuilder<PageViewQuery, Builder> {

        Builder pageId(String pageId);

        Builder browserId(String browserId);

        Builder browserIp(String browserIp);

        Builder browsingTimeFrom(Date browsingTimeFrom);

        Builder browsingTimeTo(Date browsingTimeTo);
    }
}
