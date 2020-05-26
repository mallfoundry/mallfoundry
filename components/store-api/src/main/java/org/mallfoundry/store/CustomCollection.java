package org.mallfoundry.store;

import org.mallfoundry.util.Position;

import java.util.Date;

public interface CustomCollection extends Position {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    int getProducts();

    Date getCreatedTime();
}
