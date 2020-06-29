package org.mallfoundry.rest.app;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.app.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MenuResponse {

    private String id;

    private String icon;

    private String name;

    private String url;

    private List<MenuResponse> children = new ArrayList<>();

    private int position;

    public MenuResponse(Menu menu) {
        this.setId(menu.getId());
        this.setIcon(menu.getIcon());
        this.setName(menu.getName());
        this.setUrl(menu.getUrl());
        this.setPosition(menu.getPosition());
        this.setChildren(menu.getChildren().stream().map(MenuResponse::new).collect(Collectors.toUnmodifiableList()));
    }
}
