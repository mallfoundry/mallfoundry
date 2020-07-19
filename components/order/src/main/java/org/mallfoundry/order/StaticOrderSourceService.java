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

package org.mallfoundry.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StaticOrderSourceService implements OrderSourceService {

    private final Map<OrderSource, OrderSourceDescription> descriptionMap;
    private final OrderSourceDescription defaultSourceDescription;

    public StaticOrderSourceService() {
        this.descriptionMap = this.createDescriptionMap();
        this.defaultSourceDescription = new StaticOrderSourceDescription();
    }

    @Override
    public String getSourceLabel(OrderSource source) {
        var description = this.descriptionMap.getOrDefault(source, this.defaultSourceDescription);
        return description.getLabel();
    }

    private Map<OrderSource, OrderSourceDescription> createDescriptionMap() {
        return Map.of(OrderSource.BROWSER, new StaticOrderSourceDescription());
    }

    @Getter
    @Setter
    private static class StaticOrderSourceDescription implements OrderSourceDescription {
        private OrderSource source;
        private String label;
        private String description;
        private int position;
    }
}
