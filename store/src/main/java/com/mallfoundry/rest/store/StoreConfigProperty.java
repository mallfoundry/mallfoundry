package com.mallfoundry.rest.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "store_configs")
@IdClass(StoreConfigPropertyId.class)
public class StoreConfigProperty {

    @Id
    @Column(name = "store_id_")
    private String storeId;

    @Id
    @Column(name = "name_")
    private String name;

    @Column(name = "value_")
    private String value;

    public StoreConfigProperty(String storeId, String name, String value) {
        this.storeId = storeId;
        this.name = name;
        this.value = value;
    }
}
