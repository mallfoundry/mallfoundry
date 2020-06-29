package org.mallfoundry.app.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.app.Menu;
import org.mallfoundry.app.MenuSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_app_menu")
public class JpaMenu extends MenuSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "icon_")
    private String icon;

    @Column(name = "name_")
    private String name;

    @Column(name = "url_")
    private String url;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = JpaMenu.class)
    @JoinColumn(name = "parent_id_")
    @OrderBy("position ASC")
    private List<Menu> children = new ArrayList<>();

    @Column(name = "position_")
    private int position;

    public static JpaMenu of(Menu menu) {
        if (menu instanceof JpaMenu) {
            return (JpaMenu) menu;
        }
        var target = new JpaMenu();
        BeanUtils.copyProperties(menu, target);
        return target;
    }
}
