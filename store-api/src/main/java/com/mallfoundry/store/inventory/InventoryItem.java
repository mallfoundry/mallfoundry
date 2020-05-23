package com.mallfoundry.store.inventory;

import java.util.List;

public interface InventoryItem {

    String getId();

    String getProductId();

    String getVariantId();

    List<InventoryLevel> getLevels();


}
