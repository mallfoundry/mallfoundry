package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.jpa.convert.StringSetConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_brand")
public class InternalBrand implements Brand {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @Column(name = "logo_url_")
    private String logoUrl;

    @ElementCollection
    @CollectionTable(name = "mf_brand_categories",
            joinColumns = @JoinColumn(name = "brand_id_"))
    @Column(name = "category_id_")
    private Set<String> categories;

    @Column(name = "search_keywords_")
    @Convert(converter = StringSetConverter.class)
    private Set<String> searchKeywords;

    @Column(name = "position_")
    private int position;

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
