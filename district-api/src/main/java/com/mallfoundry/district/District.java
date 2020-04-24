package com.mallfoundry.district;

import com.mallfoundry.util.Position;

public interface District extends Position {

    String getId();

    void setId(String id);

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);
}
