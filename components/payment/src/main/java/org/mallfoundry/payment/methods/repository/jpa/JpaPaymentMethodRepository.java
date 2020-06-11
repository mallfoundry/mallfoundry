package org.mallfoundry.payment.methods.repository.jpa;

import org.mallfoundry.payment.methods.InternalPaymentMethod;
import org.mallfoundry.payment.methods.PaymentMethodQuery;
import org.mallfoundry.payment.methods.PaymentMethodRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@Repository
public interface JpaPaymentMethodRepository
        extends JpaRepository<InternalPaymentMethod, String>, PaymentMethodRepository,
        JpaSpecificationExecutor<InternalPaymentMethod> {

    @Override
    default List<InternalPaymentMethod> findAll(PaymentMethodQuery methodQuery) {
        return this.findAll((Specification<InternalPaymentMethod>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(methodQuery.getEnabled())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("enabled"), methodQuery.getEnabled()));
            }
            return predicate;
        }, Sort.by(List.of(Sort.Order.asc("position"))));
    }
}
