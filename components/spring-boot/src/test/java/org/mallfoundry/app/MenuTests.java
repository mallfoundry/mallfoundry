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

import org.junit.jupiter.api.Test;
import org.mallfoundry.StaticServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MenuTests {

    @Autowired
    private MenuService menuService;

    public void createMenu(String name, String icon) {
        String iconUrl = String.format("%s/menus/%s", StaticServer.BASE_URL, icon);
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
