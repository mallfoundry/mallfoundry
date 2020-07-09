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

package org.mallfoundry.rest.checkout;

import org.mallfoundry.checkout.Checkout;
import org.mallfoundry.checkout.CheckoutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/v1")
@RestController
public class CheckoutResourceV1 {

    private final CheckoutService checkoutService;

    public CheckoutResourceV1(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    private Checkout createCheckout(String checkoutId, CheckoutRequest request) {
        return request.assignToCheckout(this.checkoutService.createCheckout(checkoutId));
    }

    @PostMapping("/checkouts")
    public Checkout checkout(@RequestBody CheckoutRequest request) {
        return checkoutService.createCheckout(this.createCheckout(null, request));
    }

    @PatchMapping("/checkouts/{id}")
    public Checkout updateCheckout(@PathVariable("id") String checkoutId, @RequestBody CheckoutRequest request) {
        return checkoutService.updateCheckout(this.createCheckout(checkoutId, request));
    }

    @GetMapping("/checkouts/{id}")
    public Optional<Checkout> getCheckout(@PathVariable("id") String checkoutId) {
        return checkoutService.getCheckout(checkoutId);
    }

    @PostMapping("/checkouts/{id}/place")
    public Checkout placeCheckout(@PathVariable("id") String checkoutId) {
        return checkoutService.placeCheckout(checkoutId);
    }
}
