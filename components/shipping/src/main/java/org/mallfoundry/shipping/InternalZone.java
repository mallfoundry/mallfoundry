package org.mallfoundry.shipping;

import org.mallfoundry.data.jpa.convert.StringListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shipping_zones")
public class InternalZone implements Zone {
    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Lob
    @Convert(converter = StringListConverter.class)
    @Column(name = "locations_")
    private List<String> locations;

    @Column(name = "first_cost_")
    private BigDecimal firstCost;

    @Column(name = "first_")
    private BigDecimal first;

    @Column(name = "additional_cost_")
    private BigDecimal additionalCost;

    @Column(name = "additional_")
    private BigDecimal additional;

    public InternalZone(String id) {
        this.id = id;
    }
}
