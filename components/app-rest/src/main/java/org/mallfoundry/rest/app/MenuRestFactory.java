package org.mallfoundry.rest.app;

import org.mallfoundry.app.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MenuRestFactory {
    private final MenuService menuService;

    public MenuRestFactory(MenuService menuService) {
        this.menuService = menuService;
    }

    @Transactional
    Optional<MenuResponse> getMenu(String id) {
        return this.menuService.getMenu(id).map(MenuResponse::new);
    }
}
