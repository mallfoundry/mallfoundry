package com.mallfoundry.rest.store.product;

import com.mallfoundry.util.Position;

import java.io.Serializable;
import java.util.List;

public interface ProductOption extends Serializable, Position {

    String getName();

    void setName(String name);

    List<ProductOptionValue> getValues();

    void setValues(List<ProductOptionValue> values);

    void addSimpleValues(List<String> values);

    void addSimpleValues(String... values);

}
