package org.mallfoundry.marketing.banner;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultBannerService implements BannerService {

    static final String BANNER_ID_VALUE_NAME = "marketing.banner.id";

    private final BannerRepository bannerRepository;

    public DefaultBannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Banner createBanner(String id) {
        return new InternalBanner(id);
    }

    @Override
    public BannerQuery createBannerQuery() {
        return new InternalBannerQuery();
    }

    @Override
    public Optional<Banner> getBanner(String id) {
        return CastUtils.cast(this.bannerRepository.findById(id));
    }

    @Override
    public SliceList<Banner> getBanners(BannerQuery query) {
        return CastUtils.cast(this.bannerRepository.findAll(query));
    }

    @Transactional
    @Override
    public Banner addBanner(Banner aBanner) {
        var banner = InternalBanner.of(aBanner);
        banner.setId(PrimaryKeyHolder.next(BANNER_ID_VALUE_NAME));
        return this.bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(Banner banner) {
        return null;
    }

    @Transactional
    @Override
    public void deleteBanner(String bannerId) {
        var banner = this.bannerRepository.findById(bannerId).orElseThrow();
        this.bannerRepository.delete(banner);
    }
}
