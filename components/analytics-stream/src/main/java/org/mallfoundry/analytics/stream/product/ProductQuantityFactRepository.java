package org.mallfoundry.analytics.stream.product;

import org.mallfoundry.analytics.models.ProductQuantityFact;

import java.util.Collection;

public interface ProductQuantityFactRepository {

    void deleteByStoreId(String storeId);

    void saveAll(Collection<ProductQuantityFact> facts);
}
