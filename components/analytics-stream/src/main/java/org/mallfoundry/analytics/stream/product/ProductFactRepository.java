package org.mallfoundry.analytics.stream.product;

import org.mallfoundry.dw.ProductFact;
import org.mallfoundry.dw.ProductQuantityFact;

import java.util.List;

public interface ProductFactRepository {

    ProductFact save(ProductFact fact);

    List<ProductQuantityFact> countAll(ProductFact fact);
}
