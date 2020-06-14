package org.mallfoundry.order.repository.jpa.convert;

import org.mallfoundry.order.InternalShipmentItem;
import org.mallfoundry.order.ShipmentItem;
import org.mallfoundry.util.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Objects;

public class ShipmentItemListConverter implements AttributeConverter<List<ShipmentItem>, String> {

    @Override
    public String convertToDatabaseColumn(List<ShipmentItem> items) {
        return Objects.isNull(items)
                ? null
                : JsonUtils.stringify(items);
    }

    @Override
    public List<ShipmentItem> convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData)
                ? null
                : JsonUtils.parse(dbData, List.class, InternalShipmentItem.class);
    }
}
