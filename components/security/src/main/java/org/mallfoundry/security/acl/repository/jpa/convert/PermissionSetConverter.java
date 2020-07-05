package org.mallfoundry.security.acl.repository.jpa.convert;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.security.acl.ImmutablePermission;
import org.mallfoundry.security.acl.Permission;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PermissionSetConverter implements AttributeConverter<Set<Permission>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Permission> attribute) {
        return CollectionUtils.isEmpty(attribute)
                ? null
                : attribute.stream().map(Permission::getMask).collect(Collectors.joining(","));
    }

    @Override
    public Set<Permission> convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData)
                ? new HashSet<>()
                : Arrays.stream(dbData.split(",")).map(ImmutablePermission::new).collect(Collectors.toSet());
    }
}
