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

public abstract class ReportSupport implements Report {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ReportSupport report;

        BuilderSupport(ReportSupport report) {
            this.report = report;
        }

        @Override
        public Builder name(String name) {
            this.report.setName(name);
            return this;
        }

        @Override
        public Builder statement(String statement) {
            this.report.setStatement(statement);
            return this;
        }

        @Override
        public Builder statementType(ReportStatementType statementType) {
            this.report.setStatementType(statementType);
            return this;
        }

        @Override
        public Report build() {
            return this.report;
        }
    }
}
