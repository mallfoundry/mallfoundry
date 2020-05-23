package com.mallfoundry.marketing.banner;

import com.mallfoundry.data.SliceList;

import java.util.Optional;

public interface BannerService {

    Banner createBanner();

    BannerQuery createBannerQuery();

    Optional<Banner> getBanner(String id);

    SliceList<Banner> getBanners(BannerQuery query);

    Banner saveBanner(Banner banner);

    void deleteBanner(String bannerId);

}
