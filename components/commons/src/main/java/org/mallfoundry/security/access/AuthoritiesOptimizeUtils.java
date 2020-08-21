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

package org.mallfoundry.security.access;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AuthoritiesOptimizeUtils {

    private static final List<MergeOptimize> OPTIMIZES = List.of(
            new MergeOptimize(
                    Set.of(
                            AllAuthorities.STORE_ROLE_ADD,
                            AllAuthorities.STORE_ROLE_READ,
                            AllAuthorities.STORE_ROLE_WRITE,
                            AllAuthorities.STORE_ROLE_DELETE
                    ),
                    Set.of(AllAuthorities.STORE_ROLE_MANAGE)
            ),
            new MergeOptimize(
                    Set.of(
                            AllAuthorities.STORE_STAFF_ADD,
                            AllAuthorities.STORE_STAFF_READ,
                            AllAuthorities.STORE_STAFF_WRITE,
                            AllAuthorities.STORE_STAFF_DELETE
                    ),
                    Set.of(AllAuthorities.STORE_STAFF_MANAGE)
            ),
            new MergeOptimize(
                    Set.of(
                            AllAuthorities.STORE_MEMBER_ADD,
                            AllAuthorities.STORE_MEMBER_READ,
                            AllAuthorities.STORE_MEMBER_WRITE,
                            AllAuthorities.STORE_MEMBER_DELETE
                    ),
                    Set.of(AllAuthorities.STORE_MEMBER_MANAGE)
            ),
            new MergeOptimize(
                    Set.of(
                            AllAuthorities.PRODUCT_COLLECTION_ADD,
                            AllAuthorities.PRODUCT_COLLECTION_READ,
                            AllAuthorities.PRODUCT_COLLECTION_WRITE,
                            AllAuthorities.PRODUCT_COLLECTION_DELETE
                    ),
                    Set.of(AllAuthorities.PRODUCT_COLLECTION_MANAGE)
            ),
            new MergeOptimize(
                    Set.of(
                            AllAuthorities.PRODUCT_ADD,
                            AllAuthorities.PRODUCT_READ,
                            AllAuthorities.PRODUCT_WRITE,
                            AllAuthorities.PRODUCT_PUBLISH,
                            AllAuthorities.PRODUCT_UNPUBLISH,
                            AllAuthorities.PRODUCT_DELETE
                    ),
                    Set.of(AllAuthorities.PRODUCT_MANAGE)
            ),
            new MergeOptimize(
                    Set.of(
                            AllAuthorities.ORDER_SHIPMENT_ADD,
                            AllAuthorities.ORDER_SHIPMENT_READ,
                            AllAuthorities.ORDER_SHIPMENT_WRITE,
                            AllAuthorities.ORDER_SHIPMENT_REMOVE
                    ),
                    Set.of(AllAuthorities.ORDER_SHIPMENT_MANAGE)
            ),
            new MergeOptimize(
                    Set.of(
                            AllAuthorities.ORDER_REFUND_READ,
                            AllAuthorities.ORDER_REFUND_APPLY,
                            AllAuthorities.ORDER_REFUND_CANCEL,
                            AllAuthorities.ORDER_REFUND_APPROVE,
                            AllAuthorities.ORDER_REFUND_DISAPPROVE,
                            AllAuthorities.ORDER_REFUND_ACTIVE
                    ),
                    Set.of(AllAuthorities.ORDER_REFUND_MANAGE)
            )
    );

    public static Set<String> optimizeAuthorities(Set<String> authorities) {
        var valveAuthorities = authorities;
        while (true) {
            var optimizedAuthorities = new HashSet<>(valveAuthorities);
            for (var optimize : OPTIMIZES) {
                optimize.optimizing(optimizedAuthorities);
            }
            valveAuthorities = Set.copyOf(optimizedAuthorities);
            if (optimizedAuthorities.containsAll(valveAuthorities)) {
                break;
            }
        }
        return valveAuthorities;
    }

    private static class MergeOptimize {
        private final Set<String> preAuthorities;
        private final Set<String> postAuthorities;

        MergeOptimize(Set<String> preAuthorities, Set<String> postAuthorities) {
            this.preAuthorities = preAuthorities;
            this.postAuthorities = postAuthorities;
        }

        public void optimizing(Set<String> allAuthorities) {
            if (allAuthorities.containsAll(this.preAuthorities)) {
                allAuthorities.removeAll(this.preAuthorities);
                allAuthorities.addAll(this.postAuthorities);
            }
        }
    }
}
