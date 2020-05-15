package com.mallfoundry.cart;

import java.util.Optional;

public interface CartRepository {

    Optional<InternalCart> findById(String id);

}
