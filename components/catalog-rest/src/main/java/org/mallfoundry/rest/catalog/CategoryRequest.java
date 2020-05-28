package org.mallfoundry.rest.catalog;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.catalog.Category;
import org.mallfoundry.catalog.CategoryVisibility;

import java.util.Set;

@Getter
@Setter
public class CategoryRequest {

    private String name;

    private String description;

    private String imageUrl;

    private Set<String> searchKeywords;

    private CategoryVisibility visibility;

    Category assignToCategory(Category category) {
        return category.toBuilder()
                .name(this.getName())
                .description(this.getDescription())
                .imageUrl(this.getImageUrl())
                .searchKeywords(this.getSearchKeywords())
                .visibility(this.getVisibility())
                .build();
    }
}
