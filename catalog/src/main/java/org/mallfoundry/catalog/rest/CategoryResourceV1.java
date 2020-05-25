package org.mallfoundry.catalog.rest;

import org.mallfoundry.catalog.CategoryService;
import org.mallfoundry.catalog.ChildCategory;
import org.mallfoundry.catalog.TopCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CategoryResourceV1 {

    private final CategoryService categoryService;

    public CategoryResourceV1(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public TopCategory createTopCategory(@RequestBody TopCategoryRequest request) {
        return this.categoryService.createTopCategory(request.getName());
    }

    @PostMapping("/categories/{category_id}/children")
    public ChildCategory addChildCategory(
            @PathVariable("category_id") Integer categoryId,
            @RequestBody ChildCategoryRequest request) {
        return this.categoryService.addChildCategory(categoryId, new ChildCategory(request.getName()));
    }

    @GetMapping("/categories")
    public List<TopCategory> getTopCategories() {
        return this.categoryService.getTopCategories();
    }

    @GetMapping("/categories/{category_id}/children")
    public List<ChildCategory> getChildCategories(
            @PathVariable("category_id") Integer categoryId) {
        return this.categoryService.getChildCategories(categoryId);
    }
}
