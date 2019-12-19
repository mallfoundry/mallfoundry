package com.mallfoundry.catalog;

import java.util.Collections;
import java.util.List;

public abstract class CategoryPositions {

    public static void sort(List<? extends Category> categories) {
        Collections.sort(categories);
        int i = 0;
        for (Category category : categories) {
            category.setPosition(i++);
        }
    }
}
