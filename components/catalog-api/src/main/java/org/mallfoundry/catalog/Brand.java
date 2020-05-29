package org.mallfoundry.catalog;

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.util.Set;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Brand extends Position {

    String getId();

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getLogoUrl();

    void setLogoUrl(String logoUrl);

    Set<String> getCategories();

    void setCategories(Set<String> categories);

    Set<String> getSearchKeywords();

    void setSearchKeywords(Set<String> searchKeywords);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Brand> {

        Builder name(String name);

        Builder description(String description);

        Builder logoUrl(String logoUrl);

        Builder categories(Set<String> categories);

        Builder searchKeywords(Set<String> searchKeywords);
    }

    abstract class BuilderSupport implements Builder {

        private final Brand brand;

        public BuilderSupport(Brand brand) {
            this.brand = brand;
        }

        @Override
        public Builder name(String name) {
            this.brand.setName(name);
            return this;
        }

        @Override
        public Builder description(String description) {
            this.brand.setDescription(description);
            return this;
        }

        @Override
        public Builder logoUrl(String logoUrl) {
            this.brand.setLogoUrl(logoUrl);
            return this;
        }

        @Override
        public Builder categories(Set<String> categories) {
            this.brand.setCategories(categories);
            return this;
        }

        @Override
        public Builder searchKeywords(Set<String> searchKeywords) {
            this.brand.setSearchKeywords(searchKeywords);
            return this;
        }

        @Override
        public Brand build() {
            return this.brand;
        }
    }
}
