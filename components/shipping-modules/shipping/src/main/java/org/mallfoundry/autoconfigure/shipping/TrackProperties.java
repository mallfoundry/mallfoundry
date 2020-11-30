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

package org.mallfoundry.autoconfigure.shipping;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

// mallfoundry.shipping.track.kdniao.e-business-id
// mallfoundry.shipping.track.kdniao.api-key
// mallfoundry.shipping.track.kdniao.url
// mallfoundry.shipping.track.kuaidi100.customer-id
// mallfoundry.shipping.track.kuaidi100.api-key
// mallfoundry.shipping.track.kuaidi100.url
//
@Getter
@Setter
@ConfigurationProperties("mallfoundry.shipping.track")
public class TrackProperties {

    TrackingType type;

    @NestedConfigurationProperty
    private Kdniao kdniao;

    @Getter
    @Setter
    static class Kdniao {
        private String url;
        private String eBusinessId;
        private String apiKey;
    }

    enum TrackingType {
        KDNIAO
    }
}
