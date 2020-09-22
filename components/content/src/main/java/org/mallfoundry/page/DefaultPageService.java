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

import java.util.List;

public class DefaultPageService implements PageService, PageProcessorInvoker {

    private final PageViewRepository pageViewRepository;

    @Setter
    private List<PageProcessor> processors;

    public DefaultPageService(PageViewRepository pageViewRepository) {
        this.pageViewRepository = pageViewRepository;
    }

    @Override
    public PageView createPageView(String id) {
        return this.pageViewRepository.create(id);
    }

    @Transactional
    @Override
    public PageView viewPage(PageView pageView) {
        pageView = this.invokePreProcessBeforeViewPage(pageView);
        pageView.setBrowserId(SubjectHolder.getSubject().getId());
        pageView.browsing();
        return this.pageViewRepository.save(pageView);
    }

    @Override
    public PageView invokePreProcessBeforeViewPage(PageView pageView) {
        return Processors.stream(this.processors)
                .map(PageProcessor::preProcessBeforeViewPage)
                .apply(pageView);
    }
}
