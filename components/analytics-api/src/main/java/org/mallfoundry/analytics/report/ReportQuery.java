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

package org.mallfoundry.analytics.report;

import org.mallfoundry.data.Query;
import org.mallfoundry.data.QueryBuilder;

import java.util.Map;

public interface ReportQuery extends Query {

    String getResultType();

    void setResultType(String resultType);

    String getReportId();

    Map<String, Object> getParameters();

    void setParameters(Map<String, Object> parameters);

    void setParameter(String name, Object value);

    Builder toBuilder();

    interface Builder extends QueryBuilder<ReportQuery, Builder> {

        Builder resultType(String resultType);

        Builder parameters(Map<String, Object> parameters);

        Builder parameter(String name, Object value);
    }
}
