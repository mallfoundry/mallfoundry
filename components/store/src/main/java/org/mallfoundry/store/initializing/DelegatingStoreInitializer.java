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

package org.mallfoundry.store.initializing;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.store.Store;
import org.mallfoundry.util.Positions;

import java.util.List;

public class DelegatingStoreInitializer implements StoreInitializer {

    @Getter
    @Setter
    private int position = 0;

    private final List<StoreInitializer> initializes;

    public DelegatingStoreInitializer(List<StoreInitializer> initializes) {
        this.initializes = ListUtils.emptyIfNull(initializes);
        Positions.sort(this.initializes);
    }

    @Override
    public void doInitialize(Store store) {
        for (var initializer : initializes) {
            initializer.doInitialize(store);
        }
    }

    @Override
    public void doConfigure(Store store) {
        for (var initializer : initializes) {
            initializer.doConfigure(store);
        }
    }
}
