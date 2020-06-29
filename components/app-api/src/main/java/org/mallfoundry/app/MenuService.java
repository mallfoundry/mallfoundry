package org.mallfoundry.app;

import java.util.Optional;

public interface MenuService {

    Menu createMenu(String id);

    Menu addMenu(Menu menu);

    Menu addMenu(String parentId, Menu menu);

    Optional<Menu> getMenu(String id);
}
