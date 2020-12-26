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

package org.mallfoundry.rest.shipping;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.shipping.rate.Rate;
import org.mallfoundry.shipping.rate.RateService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Rates")
@RestController
@RequestMapping("/v1")
public class RateResourceV1 {

    private final RateService rateService;

    public RateResourceV1(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/stores/{store_id}/shipping-rates/{rate_id}")
    public Optional<Rate> getRate(
            @PathVariable("store_id") String storeId,
            @PathVariable("rate_id") String rateId) {
        Assert.notNull(storeId, "Store id must not be null");
        return this.rateService.getRate(rateId);
    }
}
