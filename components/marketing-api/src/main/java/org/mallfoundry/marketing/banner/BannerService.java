package org.mallfoundry.marketing.banner;

import org.mallfoundry.data.SliceList;

import java.util.Optional;

public interface BannerService {

    Banner createBanner(String id);

    BannerQuery createBannerQuery();

    Optional<Banner> getBanner(String id);

    SliceList<Banner> getBanners(BannerQuery query);

    Banner addBanner(Banner banner);

    Banner updateBanner(Banner banner);

    void deleteBanner(String bannerId);

}
