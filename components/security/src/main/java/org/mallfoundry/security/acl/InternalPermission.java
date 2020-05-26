package org.mallfoundry.security.acl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class InternalPermission implements Permission {

    private String mask;

    public InternalPermission(String mask) {
        this.mask = mask;
    }

    public static InternalPermission of(Permission permission) {
        if (permission instanceof InternalPermission) {
            return (InternalPermission) permission;
        }
        return new InternalPermission(permission.getMask());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalPermission that = (InternalPermission) o;
        return Objects.equals(mask, that.mask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mask);
    }
}
