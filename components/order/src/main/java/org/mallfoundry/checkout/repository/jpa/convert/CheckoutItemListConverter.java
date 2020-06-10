package org.mallfoundry.checkout.repository.jpa.convert;

import org.mallfoundry.checkout.CheckoutItem;
import org.mallfoundry.checkout.InternalCheckoutItem;
import org.mallfoundry.util.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Objects;

public class CheckoutItemListConverter implements AttributeConverter<List<CheckoutItem>, String> {

    @Override
    public String convertToDatabaseColumn(List<CheckoutItem> items) {
        return Objects.isNull(items)
                ? null
                : JsonUtils.stringify(items);
    }

    @Override
    public List<CheckoutItem> convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData)
                ? null
                : JsonUtils.parse(dbData, List.class, InternalCheckoutItem.class);
    }
}
