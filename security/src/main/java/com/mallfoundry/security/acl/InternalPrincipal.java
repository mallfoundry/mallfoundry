package com.mallfoundry.security.acl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "acl_principals")
public class InternalPrincipal implements Principal {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "type_")
    private String type;

    @Column(name = "name_")
    private String name;

    public InternalPrincipal(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }
}
