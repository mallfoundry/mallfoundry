package org.mallfoundry.catalog.product.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepositoryDelegate extends JpaRepository<JpaProduct, String> {
}
