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

package org.mallfoundry.store.role;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.processor.ProcessorsInvoker;

import java.util.List;

public class StoreRoleProcessorsInvoker {

    private final List<StoreRoleProcessor> processors;

    public StoreRoleProcessorsInvoker(List<StoreRoleProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    public StoreRole invokePreProcessAddRole(StoreRole role) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, role, StoreRoleProcessor::preProcessAddRole);
    }

    public StoreRole invokePostProcessAddRole(StoreRole role) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, role, StoreRoleProcessor::postProcessAddRole);
    }

    public StoreRole invokePreProcessUpdateRole(StoreRole role) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, role, StoreRoleProcessor::preProcessUpdateRole);
    }

    public StoreRole invokePostProcessUpdateRole(StoreRole role) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, role, StoreRoleProcessor::postProcessUpdateRole);
    }

    public StoreRole invokePreProcessDeleteRole(StoreRole role) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, role, StoreRoleProcessor::preProcessDeleteRole);
    }
}
