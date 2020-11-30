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

import org.mallfoundry.shipping.tracking.TrackProvider;
import org.mallfoundry.shipping.tracking.provider.KdniaoTrackProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TrackProperties.class)
public class TrackAutoConfiguration {

    private final TrackProperties properties;

    public TrackAutoConfiguration(TrackProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnClass(KdniaoTrackProvider.class)
    @ConditionalOnProperty(prefix = "mallfoundry.shipping.track", name = "type", havingValue = "kdniao")
    public TrackProvider trackingProvider() {
        var config = properties.getKdniao();
        return new KdniaoTrackProvider(config.getUrl(), config.getApiKey(), config.getEBusinessId());
    }

}
