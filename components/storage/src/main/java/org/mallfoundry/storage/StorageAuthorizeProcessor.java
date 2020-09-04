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

package org.mallfoundry.storage;

import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.security.access.Resource;
import org.springframework.security.access.prepost.PreAuthorize;

public class StorageAuthorizeProcessor implements StorageProcessor {

    @PreAuthorize("hasAuthority('" + AllAuthorities.SUPER_ADMIN + "') or hasAuthority('" + AllAuthorities.ADMIN + "')")
    @Override
    public Bucket preProcessBeforeAddBucket(Bucket bucket) {
        return bucket;
    }

    @PreAuthorize("hasAuthority('" + AllAuthorities.SUPER_ADMIN + "')"
            + " or hasAuthority('" + AllAuthorities.ADMIN + "') "
            + " or hasPermission(#bucket.id, '" + Resource.STORAGE_BUCKET_TYPE + "', '"
            + AllAuthorities.STORAGE_BUCKET_READ + ","
            + AllAuthorities.STORAGE_BUCKET_MANAGE + "')")
    @Override
    public Bucket postProcessAfterGetBucket(Bucket bucket) {
        return bucket;
    }
}
