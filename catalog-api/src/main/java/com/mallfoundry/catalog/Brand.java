package com.mallfoundry.catalog;

import com.mallfoundry.util.Position;

import java.util.Set;

public interface Brand extends Position {

    String getId();

    String getName();

    String getDescription();

    String getImageUrl();

    Set<String> getSearchKeywords();
}
