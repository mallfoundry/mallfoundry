package org.mallfoundry.district;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "district_regions")
public class InternalRegion extends DistrictSupport implements Region {

    @OneToMany(targetEntity = InternalProvince.class)
    @JoinColumn(name = "region_id_")
    private List<Province> provinces = new ArrayList<>();

    @Column(name = "country_id_")
    private String countryId;

    public InternalRegion(String id, String code, String name, String countryId) {
        this.setId(id);
        this.setCode(code);
        this.setName(name);
        this.countryId = countryId;
    }

    public static InternalRegion of(Region region) {
        if (region instanceof InternalRegion) {
            return (InternalRegion) region;
        }
        var target = new InternalRegion();
        BeanUtils.copyProperties(region, target);
        return target;
    }
}
