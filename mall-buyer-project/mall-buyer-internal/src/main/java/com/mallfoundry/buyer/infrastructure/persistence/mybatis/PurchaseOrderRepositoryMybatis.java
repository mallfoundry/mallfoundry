package com.mallfoundry.buyer.infrastructure.persistence.mybatis;

import com.mallfoundry.buyer.PurchaseOrder;
import com.mallfoundry.buyer.PurchaseOrderRepository;
import com.mallfoundry.keygen.PrimaryKeyHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseOrderRepositoryMybatis implements PurchaseOrderRepository {

    private final PurchaseOrderMapper purchaseOrderMapper;

    public PurchaseOrderRepositoryMybatis(PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    @Override
    public void save(PurchaseOrder order) {
        if (StringUtils.isEmpty(order.getId())) {
            order.setId(this.nextOrderId());
        }
        order.setCreateTimeIfNull();
        this.purchaseOrderMapper.insertOrder(order);
    }

    @Override
    public void update(PurchaseOrder order) {
        this.purchaseOrderMapper.updateOrder(order);
    }

    @Override
    public void delete(String orderId) {
        this.purchaseOrderMapper.deleteOrderById(orderId);
    }

    @Override
    public List<PurchaseOrder> findListByCart(String cart) {
        return this.purchaseOrderMapper.selectListByCart(cart);
    }

    private String nextOrderId() {
        long nextOrderId = PrimaryKeyHolder.sequence().nextVal("buyer.purchase.order.id");
        return String.valueOf(10000000000000L + nextOrderId);
    }
}
