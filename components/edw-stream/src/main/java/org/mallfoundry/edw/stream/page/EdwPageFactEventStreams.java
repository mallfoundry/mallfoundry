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

package org.mallfoundry.edw.stream.page;

import org.mallfoundry.edw.page.PageFact;
import org.mallfoundry.edw.page.PageFactManager;
import org.mallfoundry.edw.time.DateDimensionManager;
import org.mallfoundry.edw.time.TimeDimensionManager;
import org.mallfoundry.page.PageView;
import org.mallfoundry.page.PageViewedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class EdwPageFactEventStreams {

    private final DateDimensionManager dateDimensionManager;

    private final TimeDimensionManager timeDimensionManager;

    private final PageFactManager pageFactManager;

    public EdwPageFactEventStreams(DateDimensionManager dateDimensionManager,
                                   TimeDimensionManager timeDimensionManager,
                                   PageFactManager pageFactManager) {
        this.dateDimensionManager = dateDimensionManager;
        this.timeDimensionManager = timeDimensionManager;
        this.pageFactManager = pageFactManager;
    }

    private PageFact createPageFact(PageView view) {
        var factKey = this.pageFactManager.createPageFactKey();
        factKey.setTenantKey(view.getTenantId());
        factKey.setStoreKey(view.getStoreId());
        factKey.setBrowserKey(view.getBrowserId());
        factKey.setBrowserIpKey(view.getBrowserIp());
        factKey.setPageKey(view.getPageId());
        factKey.setDateKey(this.dateDimensionManager.createDateDimensionKey(view.getBrowsingTime()));
        factKey.setTimeKey(this.timeDimensionManager.createTimeDimensionKey(view.getBrowsingTime()));
        var fact = this.pageFactManager.createPageFact(factKey);
        fact.setViewCount(1);
        return fact;
    }

    @EventListener
    public void onPageViewedEvent(PageViewedEvent event) {
        var view = event.getPageView();
        this.pageFactManager.savePageFact(this.createPageFact(view));
    }
}
