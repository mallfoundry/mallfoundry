package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.jpa.convert.StringSetConverter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_category")
public class InternalCategory implements Category {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Convert(converter = StringSetConverter.class)
    @Column(name = "search_keywords_")
    private Set<String> searchKeywords;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "description_")
    private String description;

    @Column(name = "parent_id_")
    private String parentId;

    @OneToMany(targetEntity = InternalCategory.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id_")
    private List<Category> children;

    @Enumerated
    @Column(name = "visibility_")
    private CategoryVisibility visibility;

    @Column(name = "position_")
    private Integer position;

    public InternalCategory(String id) {
        this.id = id;
    }

    @Override
    public Category createChildCategory(String childId) {
        return new InternalCategory(childId);
    }

    @Override
    public Optional<Category> getChildCategory(String childId) {
        return this.children.stream().filter(child -> Objects.equals(child.getId(), childId)).findFirst();
    }

    @Override
    public void setChildCategory(Category category) {

    }

    @Override
    public void addChildCategory(Category category) {
        this.children.add(category);
    }

    @Override
    public void removeChildCategory(Category category) {
        this.children.remove(category);
    }

    @Override
    public void show() {
        this.visibility = CategoryVisibility.VISIBLE;
    }

    @Override
    public void hide() {
        this.visibility = CategoryVisibility.HIDDEN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalCategory category = (InternalCategory) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
