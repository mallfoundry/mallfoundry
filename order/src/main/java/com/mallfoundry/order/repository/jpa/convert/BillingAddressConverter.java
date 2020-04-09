package com.mallfoundry.order.repository.jpa.convert;

import com.mallfoundry.order.BillingAddress;
import com.mallfoundry.order.InternalBillingAddress;
import com.mallfoundry.util.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class BillingAddressConverter implements AttributeConverter<BillingAddress, String> {
    @Override
    public String convertToDatabaseColumn(BillingAddress address) {
        return Objects.isNull(address) ? null : JsonUtils.stringify(address);
    }

    @Override
    public BillingAddress convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData) ? null : JsonUtils.parse(dbData, InternalBillingAddress.class);
    }
}
