package org.mallfoundry.cart.repository.jpa;


import org.mallfoundry.cart.CartRepository;
import org.mallfoundry.cart.InternalCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartRepository
        extends CartRepository, JpaRepository<InternalCart, String> {
}
