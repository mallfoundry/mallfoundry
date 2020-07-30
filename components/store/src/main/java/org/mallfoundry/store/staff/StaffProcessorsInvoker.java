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

package org.mallfoundry.store.staff;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.processor.ProcessorsInvoker;

import java.util.List;

public class StaffProcessorsInvoker {

    private final List<StaffProcessor> processors;

    public StaffProcessorsInvoker(List<StaffProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    public Staff invokePreProcessAddStaff(Staff staff) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, staff, StaffProcessor::preProcessAddStaff);
    }

    public Staff invokePostProcessAddStaff(Staff staff) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, staff, StaffProcessor::postProcessAddStaff);
    }

    public Staff invokePreProcessUpdateStaff(Staff staff) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, staff, StaffProcessor::preProcessUpdateStaff);
    }

    public Staff invokePostProcessUpdateStaff(Staff staff) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, staff, StaffProcessor::postProcessUpdateStaff);
    }

    public Staff invokePreProcessDeleteStaff(Staff staff) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, staff, StaffProcessor::preProcessDeleteStaff);
    }
}
