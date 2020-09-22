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

import lombok.Setter;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.security.SubjectHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DefaultPageService implements PageService, PageProcessorInvoker {

    private final PageViewRepository pageViewRepository;

    @Setter
    private List<PageProcessor> processors;

    public DefaultPageService(PageViewRepository pageViewRepository) {
        this.pageViewRepository = pageViewRepository;
    }

    @Override
    public PageViewQuery createPageViewQuery() {
        return new DefaultPageViewQuery();
    }

    @Override
    public PageView createPageView(String id) {
        return this.pageViewRepository.create(id);
    }

    private PageView getLatestViewPage(PageView pageView) {
        var latestBrowsingTime = Optional.of(pageView)
                .map(PageView::getBrowsingTime)
                .map(Date::toInstant)
                .map(instant -> instant.atZone(ZoneId.systemDefault()))
                .map(ZonedDateTime::toLocalDateTime)
                .map(time -> time.minusSeconds(10)) // 10s 窗口。
                .map(time -> time.atZone(ZoneId.systemDefault()))
                .map(ChronoZonedDateTime::toInstant)
                .map(Date::from)
                .orElseThrow();
        var query = this.createPageViewQuery().toBuilder()
                .pageId(pageView.getPageId())
                .browserId(pageView.getBrowserId())
                .browserIp(pageView.getBrowserIp())
                .browsingTimeFrom(latestBrowsingTime)
                .sort(sort -> sort.desc("browsingTime"))
                .build();
        return this.pageViewRepository.findAll(query)
                .getElements().stream().findFirst().orElse(null);
    }

    @Transactional
    @Override
    public PageView viewPage(PageView pageView) {
        pageView = this.invokePreProcessBeforeViewPage(pageView);
        pageView.setBrowserId(SubjectHolder.getSubject().getId());
        pageView.browsing();
        var latestViewPage = this.getLatestViewPage(pageView);
        if (Objects.nonNull(latestViewPage)) {
            return latestViewPage;
        }
        pageView = this.invokePreProcessAfterViewPage(pageView);
        return this.pageViewRepository.save(pageView);
    }

    @Override
    public PageView invokePreProcessBeforeViewPage(PageView pageView) {
        return Processors.stream(this.processors)
                .map(PageProcessor::preProcessBeforeViewPage)
                .apply(pageView);
    }

    @Override
    public PageView invokePreProcessAfterViewPage(PageView pageView) {
        return Processors.stream(this.processors)
                .map(PageProcessor::preProcessAfterViewPage)
                .apply(pageView);
    }
}
