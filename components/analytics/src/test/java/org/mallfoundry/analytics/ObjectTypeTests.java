package org.mallfoundry.analytics;

import org.junit.jupiter.api.Test;
import org.mallfoundry.analytics.schema.ObjectTypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ObjectTypeTests {

    @Autowired
    private ObjectTypeManager objectTypeManager;

    @Test
    public void testAddProductType() {
        var productType = this.objectTypeManager.createObjectType("product")
                .toBuilder().name("Product Type")
                .addField(p -> p.createField("id").toBuilder().type("String").build())
                .addField(p -> p.createField("name").toBuilder().type("String").build())
                .build();
        objectTypeManager.addObjectType(productType);
    }
}
