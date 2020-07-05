package org.mallfoundry.security.acl.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.security.acl.Principal;
import org.mallfoundry.security.acl.PrincipalSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_access_control_principal")
public class JpaPrincipal extends PrincipalSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "type_")
    private String type;

    @Column(name = "name_")
    private String name;

    public JpaPrincipal(String id) {
        this.id = id;
    }

    public JpaPrincipal(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public static JpaPrincipal of(Principal principal) {
        if (principal instanceof JpaPrincipal) {
            return (JpaPrincipal) principal;
        }
        var target = new JpaPrincipal();
        BeanUtils.copyProperties(principal, target);
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
        JpaPrincipal that = (JpaPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
