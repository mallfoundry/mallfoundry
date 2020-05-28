package org.mallfoundry.rest.catalog;

import org.mallfoundry.catalog.Category;
import org.mallfoundry.catalog.CategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CategoryResourceV1 {

    private final CategoryService categoryService;

    private final CategoryRestFactory categoryRestFactory;

    public CategoryResourceV1(CategoryService categoryService, CategoryRestFactory categoryRestFactory) {
        this.categoryService = categoryService;
        this.categoryRestFactory = categoryRestFactory;
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody CategoryRequest request) {
        return this.categoryService.saveCategory(request.assignToCategory(this.categoryService.createCategory()));
    }

    @PostMapping("/categories/{category_id}/children")
    public Category addChildCategory(@PathVariable("category_id") String categoryId,
                                     @RequestBody CategoryRequest request) {
        return this.categoryService.addChildCategory(categoryId,
                request.assignToCategory(this.categoryService.createCategory()));
    }

    @GetMapping("/categories")
    public List<CategoryResponse> getTopCategories(@RequestParam(defaultValue = "0", required = false) byte level) {
        return this.categoryRestFactory.getCategories(level);
    }

    @GetMapping("/categories/{category_id}/children")
    public List<CategoryResponse> getCategories(@PathVariable("category_id") String categoryId,
                                                @RequestParam(defaultValue = "0", required = false) byte level) {
        return this.categoryRestFactory.getCategories(categoryId, level);
    }

    @PatchMapping("/categories/{category_id}")
    public void saveCategory(@PathVariable("category_id") String categoryId, @RequestBody CategoryRequest request) {
        var category = this.categoryService.getCategory(categoryId).orElseThrow();
        this.categoryService.saveCategory(request.assignToCategory(category));
    }

    @DeleteMapping("/categories/{category_id}")
    public void deleteCategories(@PathVariable("category_id") String categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }
}
