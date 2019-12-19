package com.mallfoundry.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
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
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Integer id;

    @Column(name = "name_")
    private String name;

    @Column(name = "position_")
    private Integer position;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id_")
    private List<ChildCategory> children = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public ChildCategory createChildCategory(String name) {
        return new ChildCategory(this, name);
    }

    public void addChildCategory(ChildCategory childCategory) {
        childCategory.setPosition(Integer.MAX_VALUE);
        childCategory.setParent(this);
        this.getChildren().add(childCategory);
        CategoryPositions.sort(this.getChildren());
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

    @Override
    public int compareTo(Category o) {
        int selfPosition = Objects.isNull(this.getPosition()) ? Integer.MAX_VALUE : this.getPosition();
        int otherPosition = Objects.isNull(o.getPosition()) ? Integer.MAX_VALUE : o.getPosition();
        return Integer.compare(selfPosition, otherPosition);
    }
}
