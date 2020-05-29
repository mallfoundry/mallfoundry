package org.mallfoundry.catalog;


import org.mallfoundry.data.SliceList;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternalBrandService implements BrandService {

    private final BrandRepository brandRepository;

    public InternalBrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandId createBrandId(String id) {
        return new InternalBrandId(id);
    }

    @Override
    public Brand createBrand(String id) {
        return new InternalBrand(id);
    }

    @Override
    public BrandQuery createBrandQuery() {
        return new InternalBrandQuery();
    }

    @Override
    public Brand addBrand(Brand brand) throws BrandException {
        if (this.brandRepository.existsById(brand.getId())) {
            throw new BrandException("The brand id already exists");
        }
        return this.brandRepository.save(InternalBrand.of(brand));
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
