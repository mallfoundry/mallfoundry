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

package org.mallfoundry.report.page;

import java.util.List;

public class DefaultPageReport implements PageReport {

    private final PageReportRepository pageReportRepository;

    public DefaultPageReport(PageReportRepository pageReportRepository) {
        this.pageReportRepository = pageReportRepository;
    }

    @Override
    public PageQuery createPageQuery() {
        return new DefaultPageQuery();
    }

    @Override
    public List<DailyPage> queryDailyPages(PageQuery query) {
        return this.pageReportRepository.queryDailyPages(query);
    }

    @Override
    public List<MonthlyPage> queryMonthlyPages(PageQuery query) {
        return this.pageReportRepository.queryMonthlyPages(query);
    }

    @Override
    public TotalPages queryTotalPages(PageQuery query) {
        return this.pageReportRepository.queryTotalPages(query);
    }
}
