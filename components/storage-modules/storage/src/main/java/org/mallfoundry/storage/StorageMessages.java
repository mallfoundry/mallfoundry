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


import org.mallfoundry.i18n.MessageHolder;
import org.mallfoundry.i18n.MessageKeys;
import org.mallfoundry.i18n.Messages;

public abstract class StorageMessages {

    private static final MessageKeys BUCKET_KEYS = Messages.getKeys(org.mallfoundry.storage.Bucket.class);

    private static final MessageKeys BLOB_KEYS = Messages.getKeys(org.mallfoundry.storage.Blob.class);

    private static final String BUCKET_NOT_FOUND_MESSAGE_CODE_KEY = BUCKET_KEYS.codeKey("notFound");

    private static final String BLOB_NOT_FOUND_MESSAGE_CODE_KEY = BLOB_KEYS.codeKey("notFound");

    public static class Bucket {
        public static String notFound() {
            return MessageHolder.message(BUCKET_NOT_FOUND_MESSAGE_CODE_KEY);
        }
    }

    public static class Blob {
        public static String notFound() {
            return MessageHolder.message(BLOB_NOT_FOUND_MESSAGE_CODE_KEY);
        }
    }
}
