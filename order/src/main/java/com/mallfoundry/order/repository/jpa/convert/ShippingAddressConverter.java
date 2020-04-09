package com.mallfoundry.order.repository.jpa.convert;

import com.mallfoundry.order.InternalShippingAddress;
import com.mallfoundry.order.ShippingAddress;
import com.mallfoundry.util.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class ShippingAddressConverter implements AttributeConverter<ShippingAddress, String> {

    @Override
    public String convertToDatabaseColumn(ShippingAddress address) {
        return Objects.isNull(address) ? null : JsonUtils.stringify(address);
    }

    @Override
    public ShippingAddress convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData) ? null : JsonUtils.parse(dbData, InternalShippingAddress.class);
    }
}
