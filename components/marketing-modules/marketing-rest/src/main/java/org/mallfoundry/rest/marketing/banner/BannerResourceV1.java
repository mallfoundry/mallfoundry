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

package org.mallfoundry.rest.marketing.banner;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.marketing.banner.Banner;
import org.mallfoundry.marketing.banner.BannerDateType;
import org.mallfoundry.marketing.banner.BannerLocation;
import org.mallfoundry.marketing.banner.BannerPage;
import org.mallfoundry.marketing.banner.BannerService;
import org.mallfoundry.marketing.banner.BannerVisibility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag(name = "Banners")
@RestController
@RequestMapping("/v1")
public class BannerResourceV1 {

    private final BannerService bannerService;

    public BannerResourceV1(BannerService bannerService) {
        this.bannerService = bannerService;
    }


    @GetMapping("/banners")
    public SliceList<Banner> getBanners(@RequestParam(name = "pages", required = false) Set<String> pages,
                                        @RequestParam(name = "locations", required = false) Set<String> locations,
                                        @RequestParam(name = "visibilities", required = false) Set<String> visibilities,
                                        @RequestParam(name = "date_types", required = false) Set<String> dateTypes,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(name = "date_from", required = false) Date dateFrom,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(name = "date_to", required = false) Date dateTo) {
        return this.bannerService.getBanners(
                this.bannerService.createBannerQuery()
                        .toBuilder()
                        .pages(() -> Stream.ofNullable(pages).flatMap(Set::stream).filter(StringUtils::isNotBlank)
                                .map(StringUtils::upperCase).map(BannerPage::valueOf).collect(Collectors.toSet()))

                        .locations(() -> Stream.ofNullable(locations).flatMap(Set::stream).filter(StringUtils::isNotBlank)
                                .map(StringUtils::upperCase).map(BannerLocation::valueOf).collect(Collectors.toSet()))

                        .visibilities(() -> Stream.ofNullable(visibilities).flatMap(Set::stream).filter(StringUtils::isNotBlank)
                                .map(StringUtils::upperCase).map(BannerVisibility::valueOf).collect(Collectors.toSet()))

                        .dateTypes(() -> Stream.ofNullable(dateTypes).flatMap(Set::stream).filter(StringUtils::isNotBlank)
                                .map(StringUtils::upperCase).map(BannerDateType::valueOf).collect(Collectors.toSet()))
                        .dateFrom(dateFrom).dateTo(dateTo)
                        .build());
    }
}
