package org.mallfoundry.catalog;

import org.springframework.stereotype.Service;

@Service
public class InternalCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public InternalCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryId createCategoryId(String categoryId) {
        return new InternalCategoryId(categoryId);
    }

    @Override
    public Category createCategory(String id) {
        return new InternalCategory(id);
    }

    @Override
    public Category saveCategory(Category category) {
        return null;
    }

    @Override
    public Category addChildCategory(String id, Category category) {
        return null;
    }

    @Override
    public void deleteCategory(String categoryId) {
        var category = this.categoryRepository.findById(categoryId).orElseThrow();
        this.categoryRepository.delete(category);
    }
}
