package org.mallfoundry.app;

import org.mallfoundry.util.Position;

import java.util.List;

public interface Menu extends Position {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getIcon();

    void setIcon(String icon);

    String getUrl();

    void setUrl(String url);

    List<Menu> getChildren();

    void setChildren(List<Menu> children);

    void addMenu(Menu menu);

    void removeMenu(Menu menu);
}
