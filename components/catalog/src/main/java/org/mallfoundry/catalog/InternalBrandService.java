package org.mallfoundry.catalog;


import org.mallfoundry.data.SliceList;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternalBrandService implements BrandService {

    @Override
    public Brand createBrand(String id) {
        return new InternalBrand(id);
    }

    @Override
    public BrandQuery createBrandQuery() {
        return new InternalBrandQuery();
    }

    @Override
    public Brand addBrand(Brand brand) {
        return null;
    }

    @Override
    public Optional<Brand> getBrand(String id) {
        return Optional.empty();
    }

    @Override
    public SliceList<Brand> getBrands(BrandQuery query) {
        return null;
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return null;
    }

    @Override
    public void deleteBrand(String id) {

    }
}
