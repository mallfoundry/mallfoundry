package org.mallfoundry.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class InternalShipmentItem implements ShipmentItem {

    private String id;

    private String productId;

    private String variantId;

    private int quantity;

    private String name;

    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InternalShipmentItem)) {
            return false;
        }
        InternalShipmentItem that = (InternalShipmentItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
