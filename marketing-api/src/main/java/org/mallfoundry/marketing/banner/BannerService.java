package org.mallfoundry.marketing.banner;

import org.mallfoundry.data.SliceList;

import java.util.Optional;

public interface BannerService {

    Banner createBanner();

    BannerQuery createBannerQuery();

    Optional<Banner> getBanner(String id);

    SliceList<Banner> getBanners(BannerQuery query);

    Banner saveBanner(Banner banner);

    void deleteBanner(String bannerId);

}
