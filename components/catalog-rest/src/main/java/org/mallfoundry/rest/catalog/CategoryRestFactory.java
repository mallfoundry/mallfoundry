package org.mallfoundry.rest.catalog;

import org.mallfoundry.catalog.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryRestFactory {

    private final CategoryService categoryService;

    public CategoryRestFactory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Transactional
    List<CategoryResponse> getCategories(byte level) {
        return this.categoryService.getCategories()
                .stream()
                .map(category -> new CategoryResponse(category, level))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    List<CategoryResponse> getCategories(String parentId, byte level) {
        return this.categoryService.getCategories(parentId)
                .stream()
                .map(category -> new CategoryResponse(category, level))
                .collect(Collectors.toUnmodifiableList());
    }
}
