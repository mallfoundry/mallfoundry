package org.mallfoundry.catalog;

import java.util.List;
import java.util.Optional;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface CategoryService {

    CategoryId createCategoryId(String categoryId);

    Category createCategory();

    Optional<Category> getCategory(String id);

    List<Category> getCategories();

    List<Category> getCategories(String parentId);

    Category saveCategory(Category category);

    Category addChildCategory(String id, Category category);

    void deleteCategory(String categoryId);
}
