package org.mallfoundry.catalog;

import org.mallfoundry.data.SliceList;

public interface BrandRepository {

    <S extends InternalBrand> S save(S entity);

    boolean existsById(String s);

    SliceList<InternalBrand> findAll(BrandQuery query);
}
