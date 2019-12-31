package com.mallfoundry.catalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("top_category")
@NoArgsConstructor
public class TopCategory extends Category {

    @JsonIgnore
    @Override
    public List<ChildCategory> getChildren() {
        return super.getChildren();
    }

    public TopCategory(String name) {
        this(name, null);
    }

    public TopCategory(String name, CategoryIcon icon) {
        super(name, icon);
        this.setPosition(Integer.MAX_VALUE);
    }
}
