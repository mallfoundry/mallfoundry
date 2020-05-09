package com.mallfoundry.store.product;

import com.mallfoundry.util.Position;

import java.io.Serializable;

public interface ProductOptionValue extends Serializable, Position {

    String getValue();

    void setValue(String value);
}
