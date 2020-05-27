package org.mallfoundry.catalog;

import org.mallfoundry.util.Position;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Category extends Position {

    String getId();

    String getName();

    String getDescription();

    String getImageUrl();

    Set<String> getSearchKeywords();

    String getParentId();

    List<Category> getChildren();

    Category createChildCategory(String childId);

    Optional<Category> getChildCategory(String childId);

    void setChildCategory(Category category);

    void addChildCategory(Category category);

    void removeChildCategory(Category category);

    CategoryVisibility getVisibility();

    void show();

    void hide();
}
