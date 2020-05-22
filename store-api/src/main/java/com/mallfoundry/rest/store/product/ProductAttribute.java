package com.mallfoundry.rest.store.product;

import com.mallfoundry.util.Position;

import java.io.Serializable;

public interface ProductAttribute extends Serializable, Position {

    String getNamespace();

    void setNamespace(String namespace);

    String getName();

    void setName(String name);

    String getValue();

    void setValue(String value);
}
