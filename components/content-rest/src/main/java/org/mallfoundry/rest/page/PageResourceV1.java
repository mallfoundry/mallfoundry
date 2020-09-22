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

package org.mallfoundry.rest.page;

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.page.PageService;
import org.mallfoundry.page.PageView;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/v1")
@RestController
public class PageResourceV1 {

    private final PageService pageService;

    public PageResourceV1(PageService pageService) {
        this.pageService = pageService;
    }

    private String getBrowserIp(HttpServletRequest request) {
        var ip = request.getHeader("x-real-ip");
        if (StringUtils.isEmpty(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (StringUtils.contains(ip, ",")) {
            ip = ip.split(",")[0].trim();
        }
        if (StringUtils.isEmpty(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @PostMapping("/pages/{page_id}/view")
    public PageView viewPage(@PathVariable("page_id") String pageId,
                             HttpServletRequest httpRequest) {
        var pageView = this.pageService.createPageView(null);
        pageView.setPageId(pageId);
        pageView.setBrowserIp(this.getBrowserIp(httpRequest));
        return this.pageService.viewPage(pageView);
    }
}
