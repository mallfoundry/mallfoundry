package com.mallfoundry.order.repository.jpa.convert;

import com.mallfoundry.order.InternalRefundItem;
import com.mallfoundry.order.RefundItem;
import com.mallfoundry.util.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Objects;

public class RefundItemListConverter implements AttributeConverter<List<RefundItem>, String> {

    @Override
    public String convertToDatabaseColumn(List<RefundItem> items) {
        return Objects.isNull(items) ? null : JsonUtils.stringify(items);
    }

    @Override
    public List<RefundItem> convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData) ? null : JsonUtils.parse(dbData, List.class, InternalRefundItem.class);
    }
}
