package com.mallfoundry.cart.repository.jpa;


import com.mallfoundry.cart.CartRepository;
import com.mallfoundry.cart.InternalCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartRepository
        extends CartRepository, JpaRepository<InternalCart, String> {
}
