package org.mallfoundry.catalog;

public interface BrandRepository {

    <S extends InternalBrand> S save(S entity);

    boolean existsById(String s);
}
