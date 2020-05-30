package org.mallfoundry.rest.catalog;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.catalog.Category;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryResponse extends CategoryRequest {

    private String id;

    private Integer position;

    private List<CategoryResponse> children;

    public CategoryResponse(Category category) {
        this(category, 0);
    }

    public CategoryResponse(Category category, int level) {
        BeanUtils.copyProperties(category, this, "children");
        if (0 < level) {
            this.children =
                    category.getChildren()
                            .stream()
                            .map(child -> new CategoryResponse(child, level - 1))
                            .collect(Collectors.toUnmodifiableList());
        }
    }
}
