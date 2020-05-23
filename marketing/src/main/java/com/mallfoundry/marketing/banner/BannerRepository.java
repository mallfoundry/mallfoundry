package com.mallfoundry.marketing.banner;

import com.mallfoundry.data.SliceList;

import java.util.Optional;

public interface BannerRepository {

    Optional<InternalBanner> findById(String id);

    <S extends InternalBanner> S save(S entity);

    void delete(InternalBanner banner);

    SliceList<InternalBanner> findAll(BannerQuery query);
}
