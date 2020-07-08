package org.mallfoundry.analytics.stream.product;

import org.mallfoundry.analytics.models.ProductFact;
import org.mallfoundry.analytics.models.ProductQuantityFact;

import java.util.List;

public interface ProductFactRepository {

    ProductFact save(ProductFact fact);

    List<ProductQuantityFact> countAll(ProductFact fact);
}
