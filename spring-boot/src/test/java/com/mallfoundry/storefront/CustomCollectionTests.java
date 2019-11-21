package com.mallfoundry.storefront;

import com.mallfoundry.storefront.application.CustomCollectionService;
import com.mallfoundry.storefront.domain.product.CustomCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomCollectionTests {

    @Autowired
    private CustomCollectionService customCollectionService;


    @Test
    public void testAdd() {
        CustomCollection collection1 = new CustomCollection();
        collection1.setName("collection_1");
        collection1.setStoreId("store_1");
        collection1.setEnabled(true);
        this.customCollectionService.addCollection(collection1);
    }
}
