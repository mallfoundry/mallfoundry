package org.mallfoundry.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class StoreConfigPropertyId implements Serializable {

    private String storeId;

    private String name;

    public StoreConfigPropertyId(String storeId, String name) {
        this.storeId = storeId;
        this.name = name;
    }

    public static StoreConfigPropertyId of(String storeId, String name) {
        return new StoreConfigPropertyId(storeId, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StoreConfigPropertyId that = (StoreConfigPropertyId) o;
        return Objects.equals(storeId, that.storeId)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, name);
    }
}
