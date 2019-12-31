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

package com.mallfoundry.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ApplicationService applicationService;

    public void createMenu(String title, String icon) {
        String iconUrl = String.format("http://192.168.0.102:8077/static/menus/%s", icon);
        this.applicationService.createMenu("mall-h5", new Menu(title, new MenuIcon(iconUrl)));
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

    @Transactional
    @Rollback(false)
    @Test
    public void testGetMenu() {
        this.applicationService.getMenu(3482)
                .ifPresent(menu -> menu.addChildMenu(new Menu("二级子菜单2")));
    }
}
