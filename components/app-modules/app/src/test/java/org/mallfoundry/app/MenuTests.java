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

package org.mallfoundry.app;

import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StaticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MenuTests {

    @Autowired
    private MenuService menuService;

    public void createMenu(String name, String icon) {
        String iconUrl = StaticUtils.resolve(String.format("/menus/%s", icon));
        var menu = this.menuService.createMenu(null);
        menu.setName(name);
        menu.setIcon(iconUrl);
        this.menuService.addMenu("1", menu);
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testCreateMenu() {
        this.createMenu("爆款手机", "01.png");
        this.createMenu("商城超市", "02.png");
        this.createMenu("生活家电", "03.png");
        this.createMenu("商城生鲜", "04.png");
        this.createMenu("母婴玩具", "05.png");
        this.createMenu("限时抢购", "06.png");
        this.createMenu("赚钱", "07.png");
        this.createMenu("商城拼购", "08.png");
        this.createMenu("商城家电", "09.png");
        this.createMenu("签到有礼", "10.png");
    }
}
