package com.mallfoundry.store;

import com.mallfoundry.util.Position;

import java.util.Date;

public interface CustomCollection extends Position {

    String getId();

    void setId(String id);

    String getTitle();

    void setTitle(String title);

    int getProducts();

    Date getCreatedTime();
}
