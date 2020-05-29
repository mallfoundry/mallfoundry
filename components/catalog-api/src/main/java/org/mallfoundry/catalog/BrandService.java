package org.mallfoundry.catalog;

import org.mallfoundry.data.SliceList;

import java.util.Optional;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface BrandService {

    BrandId createBrandId(String id);

    Brand createBrand(String id);

    BrandQuery createBrandQuery();

    Brand addBrand(Brand brand) throws BrandException;

    Optional<Brand> getBrand(String id);

    SliceList<Brand> getBrands(BrandQuery query);

    Brand saveBrand(Brand brand);

    void deleteBrand(String id);
}
