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

package org.mallfoundry.store;


import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StaticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class StoreTests {

    @Autowired
    private StoreService storeService;

    @Rollback(false)
    @Transactional
    @Test
    public void testSaveStores() {
        this.storeService.createStore(this.storeService.createStore("mi").toBuilder().name("小米官方旗舰店").logo(ofLogoUrl("mi-logo")).build());
        this.storeService.createStore(this.storeService.createStore("mi").toBuilder().name("OPPO官方旗舰店").logo(ofLogoUrl("oppo-logo")).build());
        this.storeService.createStore(this.storeService.createStore("mi").toBuilder().name("华为官方旗舰店").logo(ofLogoUrl("huawei-logo")).build());
        this.storeService.createStore(this.storeService.createStore("mi").toBuilder().name("VIVO官方旗舰店").logo(ofLogoUrl("vivo-logo")).build());
        this.storeService.createStore(this.storeService.createStore("mi").toBuilder().name("一加官方旗舰店").logo(ofLogoUrl("oneplus-logo")).build());
    }

    private String ofLogoUrl(String name) {
        return StaticUtils.resolve("/images/" + name + ".jpg");
    }
}
