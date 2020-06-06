package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalCategoryQuery implements CategoryQuery {

    private String parentId;

    private int level;
}
