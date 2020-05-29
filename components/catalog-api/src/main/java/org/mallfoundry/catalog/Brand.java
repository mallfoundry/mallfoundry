package org.mallfoundry.catalog;

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
}
