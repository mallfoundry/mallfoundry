package org.mallfoundry.analytics.stream.product.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.analytics.models.ProductFact;
import org.mallfoundry.analytics.models.ProductQuantityFact;
import org.mallfoundry.analytics.stream.product.ProductFactRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Mapper
@Repository
public interface MybatisProductFactRepository extends ProductFactRepository {

    ProductFact selectById(String id);

    void insert(@Param("product") ProductFact fact);

    void update(@Param("product") ProductFact fact);

    @Override
    List<ProductQuantityFact> countAll(@Param("product") ProductFact fact);

    @Override
    default ProductFact save(ProductFact fact) {
        if (Objects.isNull(this.selectById(fact.getId()))) {
            this.insert(fact);
        } else {
            this.update(fact);
        }
        return this.selectById(fact.getId());
    }
}
