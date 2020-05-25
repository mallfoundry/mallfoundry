/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.mallfoundry.util.Position;
import org.mallfoundry.util.Positions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_menu")
public class Menu implements Position {

    @Column(name = "app_id_")
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    @JsonIgnore
    private String appId;

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Integer id;

    @Column(name = "title_")
    private String title;

    @Column(name = "position_")
    private Integer position;

    @JsonUnwrapped
    @Embedded
    private MenuIcon icon;

    public Menu(String title) {
        this.setTitle(title);
    }

    public Menu(String title, MenuIcon icon) {
        this.title = title;
        this.icon = icon;
    }

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    @ManyToOne
    private Menu parent;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id_")
    @OrderBy("position asc")
    private List<Menu> children = new ArrayList<>();

    public void addChildMenu(Menu menu) {
        menu.setParent(this);
        menu.setAppId(this.getAppId());
        menu.maxPosition();
        this.getChildren().add(menu);
        Positions.sort(this.getChildren());
    }

    public void maxPosition() {
        this.setPosition(Integer.MAX_VALUE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
