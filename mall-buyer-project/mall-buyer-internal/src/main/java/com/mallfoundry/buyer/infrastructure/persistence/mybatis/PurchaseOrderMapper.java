package com.mallfoundry.buyer.infrastructure.persistence.mybatis;

import com.mallfoundry.buyer.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PurchaseOrderMapper {

    void insertOrder(PurchaseOrder order);

    void updateOrder(PurchaseOrder order);

    void deleteOrderById(@Param("id") String id);

    List<PurchaseOrder> selectListByCart(@Param("cart") String cart);
}
