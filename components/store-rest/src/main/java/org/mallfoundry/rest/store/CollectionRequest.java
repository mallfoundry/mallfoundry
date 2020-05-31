package org.mallfoundry.rest.store;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.store.CustomCollection;

@Getter
@Setter
public class CollectionRequest {
    private String name;

    public CustomCollection assignToCollection(CustomCollection collection) {
        collection.setName(this.name);
        return collection;
    }
}
