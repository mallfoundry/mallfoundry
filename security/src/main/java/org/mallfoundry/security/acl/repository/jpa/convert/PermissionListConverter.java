package org.mallfoundry.security.acl.repository.jpa.convert;

import org.mallfoundry.security.acl.InternalPermission;
import org.mallfoundry.security.acl.Permission;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PermissionListConverter implements AttributeConverter<List<Permission>, String> {

    @Override
    public String convertToDatabaseColumn(List<Permission> attribute) {
        return CollectionUtils.isEmpty(attribute) ? null :
                attribute.stream().map(Permission::getMask).collect(Collectors.joining(","));
    }

    @Override
    public List<Permission> convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData) ? new ArrayList<>() :
                Arrays.stream(dbData.split(",")).map(InternalPermission::new).collect(Collectors.toList());
    }
}
