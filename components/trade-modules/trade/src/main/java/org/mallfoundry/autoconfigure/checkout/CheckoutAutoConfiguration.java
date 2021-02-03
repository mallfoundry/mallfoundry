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

package org.mallfoundry.autoconfigure.checkout;

import org.mallfoundry.cart.CartService;
import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.checkout.CheckoutRepository;
import org.mallfoundry.checkout.DefaultCheckoutService;
import org.mallfoundry.checkout.repository.jpa.DelegatingJpaCheckoutRepository;
import org.mallfoundry.checkout.repository.jpa.JpaCheckoutRepository;
import org.mallfoundry.customer.CustomerService;
import org.mallfoundry.order.OrderService;
import org.mallfoundry.store.StoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckoutAutoConfiguration {

    @Bean
    public DelegatingJpaCheckoutRepository delegatingJpaCheckoutRepository(JpaCheckoutRepository repository) {
        return new DelegatingJpaCheckoutRepository(repository);
    }

    @Bean
    public DefaultCheckoutService defaultCheckoutService(CustomerService customerService,
                                                         StoreService storeService,
                                                         CartService cartService,
                                                         ProductService productService,
                                                         OrderService orderService,
                                                         CheckoutRepository checkoutRepository) {
        return new DefaultCheckoutService(customerService, storeService, cartService, productService, orderService, checkoutRepository);
    }
}
