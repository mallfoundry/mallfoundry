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

package org.mallfoundry.app.rest;

import org.mallfoundry.app.ApplicationService;
import org.mallfoundry.app.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ApplicationResourceV1 {

    private final ApplicationService applicationService;

    public ApplicationResourceV1(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("apps/{app_id}/menus")
    public List<Menu> getMenus(@PathVariable("app_id") String appId) {
        return this.applicationService.getMenus(appId);
    }
}