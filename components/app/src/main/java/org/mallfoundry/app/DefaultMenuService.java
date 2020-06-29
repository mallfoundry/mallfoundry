package org.mallfoundry.app;

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultMenuService implements MenuService {

    static final String MENU_ID_VALUE_NAME = "menu.id";

    private final MenuRepository menuRepository;

    public DefaultMenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu createMenu(String id) {
        return null;
    }

    @Transactional
    @Override
    public Menu addMenu(Menu menu) {
        return this.menuRepository.save(menu);
    }

    @Transactional
    @Override
    public Menu addMenu(String parentId, Menu menu) {
        var parentMenu = this.menuRepository.findById(parentId).orElseThrow();
        if (Objects.isNull(menu.getId())) {
            menu.setId(PrimaryKeyHolder.next(MENU_ID_VALUE_NAME));
        }
        parentMenu.addMenu(menu);
        this.menuRepository.save(parentMenu);
        return menu;
    }

    @Override
    public Optional<Menu> getMenu(String id) {
        return this.menuRepository.findById(id);
    }
}
