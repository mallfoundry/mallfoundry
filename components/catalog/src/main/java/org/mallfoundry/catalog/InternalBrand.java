package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class InternalBrand implements Brand {

    private String id;

    private String name;

    private String description;

    private String imageUrl;

    private Set<String> categories;

    private Set<String> searchKeywords;

    private long position;

    public InternalBrand(String id) {
        this.id = id;
    }
}
