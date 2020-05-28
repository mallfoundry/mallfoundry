package org.mallfoundry.catalog;

import org.mallfoundry.data.Pageable;
import org.mallfoundry.data.PageableBuilder;
import org.mallfoundry.data.PageableBuilderSupport;

import java.util.Set;

public interface BrandQuery extends Pageable {

    Set<String> getCategories();

    void setCategories(Set<String> categories);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends PageableBuilder<BrandQuery, Builder> {
        Builder categories(Set<String> categories);
    }

    abstract class BuilderSupport extends PageableBuilderSupport<BrandQuery, Builder> implements Builder {

        private final BrandQuery query;

        public BuilderSupport(BrandQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder categories(Set<String> categories) {
            this.query.setCategories(categories);
            return this;
        }

        @Override
        public BrandQuery build() {
            return this.query;
        }
    }
}
