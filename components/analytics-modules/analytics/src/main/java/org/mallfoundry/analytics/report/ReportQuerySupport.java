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

import org.apache.commons.collections4.MapUtils;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;

import java.util.Map;

public abstract class ReportQuerySupport extends QuerySupport implements ReportQuery {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport
            extends QueryBuilderSupport<ReportQuery, Builder> implements Builder {

        protected final ReportQuery query;

        protected BuilderSupport(ReportQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder resultType(String resultType) {
            this.query.setResultType(resultType);
            return this;
        }

        @Override
        public Builder parameters(Map<String, Object> parameters) {
            if (MapUtils.isNotEmpty(parameters)) {
                parameters.forEach(this::parameter);
            }
            return this;
        }

        @Override
        public Builder parameter(String name, Object value) {
            this.query.setParameter(name, value);
            return this;
        }
    }
}
