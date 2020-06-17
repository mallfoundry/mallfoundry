package org.mallfoundry.elasticsearch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mallfoundry.catalog.product.repository.elasticsearch.ElasticsearchProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ElasticsearchTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Test
    public void testPutMapping() {
        var indexOperations = elasticsearchRestTemplate.indexOps(ElasticsearchProduct.class);
        var mapping = indexOperations.createMapping();
        indexOperations.putMapping(mapping);
    }
}
