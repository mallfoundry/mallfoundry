/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
