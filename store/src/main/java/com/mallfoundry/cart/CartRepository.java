package com.mallfoundry.cart;

import java.util.Optional;

public interface CartRepository {

    <S extends InternalCart> S save(S s);

    void delete(InternalCart cart);

    Optional<InternalCart> findById(String id);
}
