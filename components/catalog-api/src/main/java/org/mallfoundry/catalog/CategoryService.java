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

    CategoryQuery createCategoryQuery();

    Optional<Category> getCategory(String id);

    List<Category> getCategories(CategoryQuery query);

    Category addCategory(Category category);

    Category updateCategory(Category category);

    Category addChildCategory(String id, Category category);

    void deleteCategory(String categoryId);
}
