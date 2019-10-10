package com.mallfoundry.buyer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultShoppingCartService implements ShoppingCartService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public DefaultShoppingCartService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional
    @Override
    public void addOrder(PurchaseOrder order) {
        this.purchaseOrderRepository.save(order);
    }

    @Override
    public void removeOrder(PurchaseOrder order) {
        this.purchaseOrderRepository.delete(order.getId());
    }

    @Override
    public void updateOrder(PurchaseOrder order) {
        this.purchaseOrderRepository.update(order);
    }

    @Override
    public List<PurchaseOrder> getOrders(String cart) {
        return this.purchaseOrderRepository.findListByCart(cart);
    }
}
