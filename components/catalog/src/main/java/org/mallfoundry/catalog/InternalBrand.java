package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class InternalBrand implements Brand {

    private String id;

    private String name;

    private String description;

    private String logoUrl;

    private Set<String> categories;

    private Set<String> searchKeywords;

    private long position;

    public InternalBrand(String id) {
        this.id = id;
    }

    public static InternalBrand of(Brand brand) {
        if (brand instanceof InternalBrand) {
            return (InternalBrand) brand;
        }
        var target = new InternalBrand();
        BeanUtils.copyProperties(brand, target);
        return target;
    }
}
