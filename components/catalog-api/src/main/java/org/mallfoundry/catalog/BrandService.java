package org.mallfoundry.catalog;

import org.mallfoundry.data.SliceList;

import java.util.Optional;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface BrandService {

    Brand createBrand(String id);

    BrandQuery createBrandQuery();

    Brand addBrand(Brand brand);

    Optional<Brand> getBrand(String id);

    SliceList<Brand> getBrands(BrandQuery query);

    Brand saveBrand(Brand brand);

    void deleteBrand(String id);
}
