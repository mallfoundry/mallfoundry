package com.mallfoundry.catalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("child_category")
public class ChildCategory extends Category {

    @JsonIgnore
    @ManyToOne
    private Category parent;

    public ChildCategory(String name) {
        super(name);
    }

    public ChildCategory(Category parent, String name) {
        super(name);
        this.setParent(parent);
    }
}
