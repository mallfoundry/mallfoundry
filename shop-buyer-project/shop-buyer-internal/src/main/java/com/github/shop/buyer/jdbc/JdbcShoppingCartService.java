package com.github.shop.buyer.jdbc;

import com.github.shop.buyer.PurchaseOrder;
import com.github.shop.buyer.PurchaseOrderRepository;
import com.github.shop.buyer.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JdbcShoppingCartService implements ShoppingCartService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public JdbcShoppingCartService(PurchaseOrderRepository purchaseOrderRepository) {
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
