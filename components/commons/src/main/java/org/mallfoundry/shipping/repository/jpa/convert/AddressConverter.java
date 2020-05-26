package org.mallfoundry.shipping.repository.jpa.convert;

import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.DefaultAddress;
import org.mallfoundry.util.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class AddressConverter implements AttributeConverter<Address, String> {

    @Override
    public String convertToDatabaseColumn(Address address) {
        return Objects.isNull(address) ? null : JsonUtils.stringify(address);
    }

    @Override
    public Address convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData) ? null : JsonUtils.parse(dbData, DefaultAddress.class);
    }
}
