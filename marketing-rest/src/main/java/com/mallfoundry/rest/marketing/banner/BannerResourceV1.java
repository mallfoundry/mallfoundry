package com.mallfoundry.rest.marketing.banner;


import com.mallfoundry.data.SliceList;
import com.mallfoundry.marketing.banner.Banner;
import com.mallfoundry.marketing.banner.BannerDateType;
import com.mallfoundry.marketing.banner.BannerLocation;
import com.mallfoundry.marketing.banner.BannerPage;
import com.mallfoundry.marketing.banner.BannerService;
import com.mallfoundry.marketing.banner.BannerVisibility;
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
