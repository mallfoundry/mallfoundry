package org.mallfoundry.catalog;

import java.util.List;
import java.util.Optional;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface CategoryService {

    CategoryId createCategoryId(String categoryId);

    Category createCategory(String id);

    Optional<Category> getCategory(String id);

    List<Category> getCategories();

    List<Category> getCategories(String parentId);

    Category addCategory(Category category);

    Category updateCategory(Category category);

    Category addChildCategory(String id, Category category);

    void deleteCategory(String categoryId);
}
