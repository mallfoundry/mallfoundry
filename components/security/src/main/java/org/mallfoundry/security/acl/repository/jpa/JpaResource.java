package org.mallfoundry.security.acl.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.security.acl.MutableResource;
import org.mallfoundry.security.acl.Resource;
import org.mallfoundry.security.acl.ResourceUnavailableException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_access_control_resource")
public class JpaResource implements MutableResource {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "type_")
    private String type;

    @Column(name = "identifier_")
    private String identifier;

    public JpaResource(String id) {
        this.id = id;
    }

    public JpaResource(String id, String type, String identifier) {
        this.id = id;
        this.type = type;
        this.identifier = identifier;
    }

    public JpaResource(String id, Object object) throws ResourceUnavailableException {
        this.id = id;
        Class<?> typeClass = ClassUtils.getUserClass(object.getClass());
        this.type = typeClass.getName();

        Object result;

        try {
            Method method = typeClass.getMethod("getId");
            method.setAccessible(true);
            result = method.invoke(object);
        } catch (Exception e) {
            throw new ResourceUnavailableException("Could not extract resource from object " + object, e);
        }

        Assert.notNull(result, "getId() is required to return a non-null value");
        Assert.isInstanceOf(Serializable.class, result, "Getter must provide a return value of type Serializable");
        this.identifier = String.valueOf(result);
    }

    public static JpaResource of(Resource resource) {
        if (resource instanceof JpaResource) {
            return (JpaResource) resource;
        }
        var target = new JpaResource();
        BeanUtils.copyProperties(resource, target);
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JpaResource that = (JpaResource) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
