package org.mallfoundry.rest.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class MenuResourceV1 {

    private final MenuRestFactory menuRestFactory;

    public MenuResourceV1(MenuRestFactory menuRestFactory) {
        this.menuRestFactory = menuRestFactory;
    }

    @GetMapping("menus/{id}")
    public Optional<MenuResponse> getMenus(@PathVariable("id") String id) {
        return this.menuRestFactory.getMenu(id);
    }
}
