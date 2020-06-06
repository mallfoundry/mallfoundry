package org.mallfoundry.catalog;

import org.mallfoundry.util.ObjectBuilder;

public interface CategoryQuery {

    String getParentId();

    void setParentId(String parentId);

    int getLevel();

    void setLevel(int level);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<CategoryQuery> {

        Builder parentId(String parentId);

        Builder level(int level);
    }

    abstract class BuilderSupport implements Builder {

        protected final CategoryQuery query;

        public BuilderSupport(CategoryQuery query) {
            this.query = query;
        }

        @Override
        public Builder parentId(String parentId) {
            this.query.setParentId(parentId);
            return this;
        }

        @Override
        public Builder level(int level) {
            this.query.setLevel(level);
            return this;
        }

        @Override
        public CategoryQuery build() {
            return this.query;
        }
    }
}
