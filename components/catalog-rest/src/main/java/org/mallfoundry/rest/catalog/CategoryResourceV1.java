package org.mallfoundry.rest.catalog;

import org.mallfoundry.catalog.Category;
import org.mallfoundry.catalog.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CategoryResourceV1 {

    private final CategoryService categoryService;

    public CategoryResourceV1(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody TopCategoryRequest request) {
        return this.categoryService.createCategory(request.getName());
    }

//    @PostMapping("/categories/{category_id}/children")
//    public Category addChildCategory(
//            @PathVariable("category_id") Integer categoryId,
//            @RequestBody ChildCategoryRequest request) {
//        return this.categoryService.addChildCategory(categoryId, new ChildCategory(request.getName()));
//    }
//
//    @GetMapping("/categories")
//    public List<Category> getTopCategories() {
//        return this.categoryService.getTopCategories();
//    }
//
//    @GetMapping("/categories/{category_id}/children")
//    public List<Category> getChildCategories(
//            @PathVariable("category_id") Integer categoryId) {
//        return this.categoryService.getChildCategories(categoryId);
//    }
}
