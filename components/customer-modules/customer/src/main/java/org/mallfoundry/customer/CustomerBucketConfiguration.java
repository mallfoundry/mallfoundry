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

package org.mallfoundry.customer;

import org.mallfoundry.storage.Bucket;
import org.mallfoundry.storage.MediaBucket;
import org.mallfoundry.storage.StorageService;
import org.mallfoundry.util.ObjectType;
import org.springframework.context.event.EventListener;

public class CustomerBucketConfiguration {

    private final StorageService storageService;

    public CustomerBucketConfiguration(StorageService storageService) {
        this.storageService = storageService;
    }

    private Bucket createCustomerBucket(Customer customer) {
        var bucketId = this.storageService.createBucketId(
                ObjectType.CUSTOMER.code() + MediaBucket.ALL.code() + customer.getId());
        return this.storageService.createBucket(bucketId);
    }

    @EventListener
    public void onCustomerAddedEvent(CustomerAddedEvent event) {
        var bucket = this.createCustomerBucket(event.getCustomer());
        this.storageService.addBucket(bucket);
    }
}
