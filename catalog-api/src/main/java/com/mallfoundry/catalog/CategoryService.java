package com.mallfoundry.catalog;

public interface CategoryService {

    CategoryId createCategoryId(String categoryId);

    Category createCategory();

    Category createCategory(CategoryId parentId);

    Category saveCategory(Category category);

    void deleteCategory(String categoryId);

}
