package org.mallfoundry.analytics.schema;

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
                .addField(p -> p.createField("id").toBuilder().label("商品标识").type("String").build())
                .addField(p -> p.createField("name").toBuilder().label("商品名称").type("String").build())
                .build();
        objectTypeManager.addObjectType(productType);
    }
}
