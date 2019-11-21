package com.mallfoundry.storefront.infrastructure.persistence.mybatis.product;

import com.mallfoundry.storefront.domain.product.ProductVariant;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductVariantMapper {

    void insertAll(List<ProductVariant> variants);
}
