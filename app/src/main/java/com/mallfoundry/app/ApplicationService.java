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

import com.mallfoundry.util.Positions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final MenuRepository menuRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              MenuRepository menuRepository) {
        this.applicationRepository = applicationRepository;
        this.menuRepository = menuRepository;
    }

    public Optional<Application> getApplication(String id) {
        return this.applicationRepository.findById(id);
    }

    @Transactional
    public void createMenu(String appId, Menu menu) {
        menu.maxPosition();
        menu.setAppId(appId);
        this.menuRepository.save(menu);
        List<Menu> menus = this.menuRepository.findAllByAppId(menu.getAppId());
        Positions.sort(menus);
    }

    @Transactional
    public void addMenu(Integer parentId, Menu child) {
        this.menuRepository
                .findById(parentId)
                .ifPresent(menu -> menu.addChildMenu(child));
    }

    public List<Menu> getMenus(String appId) {
        return this.menuRepository.findAllByAppId(appId);
    }


    @Transactional
    public Optional<Menu> getMenu(Integer menuId) {
        return this.menuRepository.findById(menuId);
    }
}
