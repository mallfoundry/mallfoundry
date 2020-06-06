package org.mallfoundry.rest.catalog;

import org.mallfoundry.catalog.CategoryQuery;
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
    List<CategoryResponse> getCategories(CategoryQuery query) {
        return this.categoryService.getCategories(query)
                .stream()
                .map(category -> new CategoryResponse(category, query.getLevel()))
                .collect(Collectors.toUnmodifiableList());
    }
}
