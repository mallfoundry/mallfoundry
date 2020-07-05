package org.mallfoundry.security.acl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ImmutablePermission implements Permission {

    private String mask;

    public ImmutablePermission(String mask) {
        this.mask = mask;
    }

    public static ImmutablePermission of(Permission permission) {
        if (permission instanceof ImmutablePermission) {
            return (ImmutablePermission) permission;
        }
        return new ImmutablePermission(permission.getMask());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImmutablePermission that = (ImmutablePermission) o;
        return Objects.equals(mask, that.mask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mask);
    }
}
