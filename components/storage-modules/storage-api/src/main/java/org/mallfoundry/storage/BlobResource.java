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

import org.mallfoundry.util.ObjectBuilder;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface BlobResource extends Closeable, ObjectBuilder.ToBuilder<BlobResource.Builder> {

    BlobPath toPath();

    String getBucketId();

    void setBucketId(String bucketId);

    String getPath();

    void setPath(String path);

    String getName();

    void setName(String name);

    String getUrl();

    void setUrl(String url);

    long getSize();

    void setSize(long size);

    BlobType getType();

    void setType(BlobType type);

    String getContentType();

    void setContentType(String contentType);

    boolean isReadable();

    InputStream getInputStream() throws IOException;

    File toFile() throws IOException;

    interface Builder extends ObjectBuilder<BlobResource> {

        Builder bucketId(String bucketId);

        Builder path(String path);

        Builder name(String name);

        Builder type(BlobType type);

        Builder contentType(String contentType);

        Builder inputStream(InputStream inputStream) throws IOException;

        Builder file(File file);
    }
}
