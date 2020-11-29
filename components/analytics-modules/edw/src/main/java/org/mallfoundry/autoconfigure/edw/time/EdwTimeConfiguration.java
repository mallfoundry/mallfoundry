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

package org.mallfoundry.autoconfigure.edw.time;

import org.mallfoundry.edw.time.DateDimensionRepository;
import org.mallfoundry.edw.time.DefaultDateDimensionManager;
import org.mallfoundry.edw.time.DefaultTimeDimensionManager;
import org.mallfoundry.edw.time.TimeDimensionRepository;
import org.mallfoundry.edw.time.jpa.DelegatingJpaDateDimensionRepository;
import org.mallfoundry.edw.time.jpa.DelegatingJpaTimeDimensionRepository;
import org.mallfoundry.edw.time.jpa.JpaDateDimensionRepository;
import org.mallfoundry.edw.time.jpa.JpaTimeDimensionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EdwTimeConfiguration {

    @Bean
    public DelegatingJpaDateDimensionRepository delegatingJpaDateDimensionRepository(JpaDateDimensionRepository repository) {
        return new DelegatingJpaDateDimensionRepository(repository);
    }

    @Bean
    public DefaultDateDimensionManager defaultDateDimensionManager(DateDimensionRepository dateDimensionRepository) {
        return new DefaultDateDimensionManager(dateDimensionRepository);
    }

    @Bean
    public DelegatingJpaTimeDimensionRepository delegatingJpaTimeDimensionRepository(JpaTimeDimensionRepository repository) {
        return new DelegatingJpaTimeDimensionRepository(repository);
    }

    @Bean
    public DefaultTimeDimensionManager defaultTimeDimensionManager(TimeDimensionRepository timeDimensionRepository) {
        return new DefaultTimeDimensionManager(timeDimensionRepository);
    }
}
