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
        return new DefaultBannerQuery();
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
