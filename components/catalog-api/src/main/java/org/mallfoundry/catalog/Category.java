package org.mallfoundry.catalog;

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.util.List;
import java.util.Set;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Category extends Position {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    Set<String> getSearchKeywords();

    void setSearchKeywords(Set<String> searchKeywords);

    String getParentId();

    List<Category> getChildren();

    Category createCategory();

    void addCategory(Category category);

    void removeCategory(Category category);

    CategoryVisibility getVisibility();

    void show();

    void hide();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Category> {

        Builder name(String name);

        Builder description(String description);

        Builder imageUrl(String imageUrl);

        Builder searchKeywords(Set<String> searchKeywords);

        Builder visibility(CategoryVisibility visibility);

        Builder show();

        Builder hide();

    }

    abstract class BuilderSupport implements Builder {

        private final Category category;

        public BuilderSupport(Category category) {
            this.category = category;
        }

        @Override
        public Builder name(String name) {
            this.category.setName(name);
            return this;
        }

        @Override
        public Builder description(String description) {
            this.category.setDescription(description);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.category.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public Builder searchKeywords(Set<String> searchKeywords) {
            this.category.setSearchKeywords(searchKeywords);
            return this;
        }

        @Override
        public Builder visibility(CategoryVisibility visibility) {
            if (visibility == CategoryVisibility.VISIBLE) {
                this.category.show();
            } else if (visibility == CategoryVisibility.HIDDEN) {
                this.category.hide();
            }
            return this;
        }

        @Override
        public Builder show() {
            this.category.show();
            return this;
        }

        @Override
        public Builder hide() {
            this.category.hide();
            return this;
        }

        @Override
        public Category build() {
            return this.category;
        }
    }
}
