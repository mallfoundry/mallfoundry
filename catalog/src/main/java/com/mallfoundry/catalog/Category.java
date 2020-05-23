package com.mallfoundry.catalog;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mallfoundry.util.Position;
import com.mallfoundry.util.Positions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "catalog_category")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_")
public class Category implements Position {

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Integer id;

    @Column(name = "name_")
    private String name;

    @Column(name = "search_keywords_")
    private String searchKeywords;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "position_")
    private Integer position;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id_")
    private List<ChildCategory> children = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public ChildCategory createChildCategory(String name) {
        return new ChildCategory(this, name);
    }

    public void addChildCategory(ChildCategory childCategory) {
        childCategory.setPosition(Integer.MAX_VALUE);
        childCategory.setParent(this);
        this.getChildren().add(childCategory);
        Positions.sort(this.getChildren());
    }

    public void removeChildCategory(ChildCategory childCategory) {
        this.getChildren().remove(childCategory);
    }

    public void clearChildCategories() {
        this.getChildren().clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
