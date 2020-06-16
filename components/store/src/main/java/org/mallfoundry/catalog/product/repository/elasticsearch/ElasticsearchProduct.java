package org.mallfoundry.catalog.product.repository.elasticsearch;

import org.mallfoundry.catalog.product.ProductSupport;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "product")
public class ElasticsearchProduct extends ProductSupport {

    public ElasticsearchProduct(String id) {
        super(id);
    }
}
