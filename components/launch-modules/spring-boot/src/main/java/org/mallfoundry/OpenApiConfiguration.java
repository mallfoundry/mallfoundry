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

package org.mallfoundry;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> openApi
                .info(new Info().title("Mallfoundry REST API").version("0.0.1"))
                .extensions(Map.of("x-tagGroups", List.of(
                Map.of("name", "Keystone", "tags", List.of("Users", "Authorities")),
                Map.of("name", "Catalog", "tags", List.of("Brands", "Categories", "Product Collections", "Products")),
                Map.of("name", "Customers", "tags", List.of("Customers", "Customer Search Terms", "Customer Browsing", "Customer Follow")),
                Map.of("name", "Stores", "tags", List.of("Stores", "Store Staffs", "Store Roles", "Store Members")),
                Map.of("name", "Marketing", "tags", List.of("Banners", "Coupons")),
                Map.of("name", "Orders", "tags", List.of("Checkouts", "Carts", "Orders", "Order Reviews", "Order Disputes")),
                Map.of("name", "Shipping", "tags", List.of("Carriers", "Rates", "Tracking")),
                Map.of("name", "Storage", "tags", List.of("Blobs")),
                Map.of("name", "Analytics", "tags", List.of("Product Reports", "Order Reports", "Page Reports", "Sales Reports", "Store Reports"))
        )));
    }
}
