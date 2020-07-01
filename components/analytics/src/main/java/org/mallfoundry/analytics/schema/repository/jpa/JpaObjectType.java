package org.mallfoundry.analytics.schema.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.analytics.schema.ObjectField;
import org.mallfoundry.analytics.schema.ObjectType;
import org.mallfoundry.analytics.schema.ObjectTypeSupport;
import org.mallfoundry.analytics.schema.repository.jpa.convert.ObjectFieldListConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_object_type")
public class JpaObjectType extends ObjectTypeSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Convert(converter = ObjectFieldListConverter.class)
    @Column(name = "fields_", length = 1024 * 2)
    private List<ObjectField> fields = new LinkedList<>();

    public JpaObjectType(String id) {
        this.id = id;
    }

    public static JpaObjectType of(ObjectType objectType) {
        if (objectType instanceof JpaObjectType) {
            return (JpaObjectType) objectType;
        }
        var target = new JpaObjectType();
        BeanUtils.copyProperties(objectType, target);
        return target;
    }
}
