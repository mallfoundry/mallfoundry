package org.mallfoundry.catalog.product.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticsearchProductRepositoryDelegate
        extends ElasticsearchRepository<ElasticsearchProduct, String> {
}
