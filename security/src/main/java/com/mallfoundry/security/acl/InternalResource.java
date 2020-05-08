package com.mallfoundry.security.acl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Method;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "acl_resources")
public class InternalResource implements Resource {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "identifier_")
    private String identifier;

    @Column(name = "type_")
    private String type;

    public InternalResource(String id, String type, String identifier) {
        this.id = id;
        this.type = type;
        this.identifier = identifier;
    }

    public InternalResource(String id, Object object) throws ResourceUnavailableException {
        this.id = id;
        Class<?> typeClass = ClassUtils.getUserClass(object.getClass());
        this.type = typeClass.getName();

        Object result;

        try {
            Method method = typeClass.getMethod("getId");
            result = method.invoke(object);
        } catch (Exception e) {
            throw new ResourceUnavailableException("Could not extract resource from object " + object, e);
        }

        Assert.notNull(result, "getId() is required to return a non-null value");
        Assert.isInstanceOf(Serializable.class, result, "Getter must provide a return value of type Serializable");
        this.identifier = String.valueOf(result);
    }
}
