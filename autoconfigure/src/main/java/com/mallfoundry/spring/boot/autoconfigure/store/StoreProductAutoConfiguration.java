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

package com.mallfoundry.spring.boot.autoconfigure.store;

import com.mallfoundry.catalog.search.ProductSearchProvider;
import com.mallfoundry.catalog.search.ProductSearcher;
import com.mallfoundry.catalog.search.LuceneProductSearchProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StoreProductProperties.class)
public class StoreProductAutoConfiguration {

    @Bean
    @ConditionalOnClass(ProductSearcher.class)
    public ProductSearchProvider productSearchService(StoreProductProperties properties) {
        StoreProductProperties.Search search = properties.getSearch();
        if (search.getType() == StoreProductProperties.SearchType.LUCENE) {
            return new LuceneProductSearchProvider(search.getLucene().getDirectory());
        }
        return null;
    }
}
