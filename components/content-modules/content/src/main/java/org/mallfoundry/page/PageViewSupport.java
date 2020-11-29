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

import java.util.Date;

public abstract class PageViewSupport implements MutablePageView {

    @Override
    public void browsing() {
        this.setBrowsingTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final PageViewSupport pageView;

        protected BuilderSupport(PageViewSupport pageView) {
            this.pageView = pageView;
        }

        @Override
        public Builder id(String id) {
            this.pageView.setId(id);
            return this;
        }

        @Override
        public Builder pageId(String pageId) {
            this.pageView.setPageId(pageId);
            return this;
        }

        @Override
        public Builder pageType(PageType pageType) {
            this.pageView.setPageType(pageType);
            return this;
        }

        @Override
        public Builder browserId(String browserId) {
            this.pageView.setBrowserId(browserId);
            return this;
        }

        @Override
        public Builder browserIp(String browserIp) {
            this.pageView.setBrowserIp(browserIp);
            return this;
        }

        @Override
        public PageView build() {
            return this.pageView;
        }
    }
}
