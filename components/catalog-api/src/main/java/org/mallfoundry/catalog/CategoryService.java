package org.mallfoundry.catalog;

public interface CategoryService {

    CategoryId createCategoryId(String categoryId);

    Category createCategory(String id);

    Category saveCategory(Category category);

    Category addChildCategory(String id, Category category);

    void deleteCategory(String categoryId);
}
