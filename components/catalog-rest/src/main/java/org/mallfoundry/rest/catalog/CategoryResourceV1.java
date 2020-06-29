package org.mallfoundry.rest.catalog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
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


@Tag(name = "Category Resource V1", description = "商品类目资源")
@RestController
@RequestMapping("/v1")
public class CategoryResourceV1 {

    private final CategoryService categoryService;

    private final CategoryRestFactory categoryRestFactory;

    public CategoryResourceV1(CategoryService categoryService, CategoryRestFactory categoryRestFactory) {
        this.categoryService = categoryService;
        this.categoryRestFactory = categoryRestFactory;
    }

    @Operation(summary = "添加商品类目")
    @PostMapping("/categories")
    public CategoryResponse createCategory(@RequestBody CategoryRequest request) {
        if (StringUtils.isBlank(request.getParentId())) {
            return new CategoryResponse(
                    this.categoryService.addCategory(request.assignToCategory(this.categoryService.createCategory(null))));
        } else {
            return new CategoryResponse(
                    this.categoryService.addCategory(request.getParentId(),
                            request.assignToCategory(this.categoryService.createCategory(null))));
        }
    }

    @Operation(summary = "获得商品类目集合")
    @GetMapping("/categories")
    public List<CategoryResponse> getCategories(@Parameter(description = "类目层级")
                                                @RequestParam(defaultValue = "0", required = false) byte level,
                                                @RequestParam(name = "parent_id", required = false) String parentId) {
        return this.categoryRestFactory.getCategories(
                this.categoryService.createCategoryQuery().toBuilder().level(level).parentId(parentId).build());
    }

    @Operation(summary = "根据商品类目标识更新商品类目对象")
    @PatchMapping("/categories/{category_id}")
    public void updateCategory(@PathVariable("category_id") String categoryId, @RequestBody CategoryRequest request) {
        var category = this.categoryService.createCategory(categoryId);
        this.categoryService.updateCategory(request.assignToCategory(category));
    }

    @Operation(summary = "根据商品类目标识删除商品类目对象")
    @DeleteMapping("/categories/{category_id}")
    public void deleteCategories(@PathVariable("category_id") String categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }
}
