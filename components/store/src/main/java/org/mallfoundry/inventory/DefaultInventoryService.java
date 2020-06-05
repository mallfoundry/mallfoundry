package org.mallfoundry.inventory;

import org.mallfoundry.catalog.product.ProductService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultInventoryService implements InventoryService {

    private final ProductService productService;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultInventoryService(ProductService productService,
                                   ApplicationEventPublisher eventPublisher) {
        this.productService = productService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public InventoryAdjustment createInventoryAdjustment() {
        return new InternalInventoryAdjustment();
    }

    @Override
    public void adjustInventory(InventoryAdjustment adjustment) {
        this.productService.adjustInventory(adjustment);
        this.eventPublisher.publishEvent(new InternalInventoryAdjustedEvent(adjustment));
    }

    @Override
    public void adjustInventories(List<InventoryAdjustment> adjustments) {

    }

}
