package com.mallfoundry.catalog;

import com.mallfoundry.util.Position;

import java.util.List;
import java.util.Set;

public interface Category extends Position {

    String getId();

    String getName();

    String getDescription();

    String getImageUrl();

    Set<String> getSearchKeywords();

    String getParentId();

    List<Category> getChildren();

    CategoryVisibility getVisibility();

    void show();

    void hide();
}
