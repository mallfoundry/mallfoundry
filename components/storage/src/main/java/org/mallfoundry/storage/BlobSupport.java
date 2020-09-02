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

public abstract class BlobSupport/* implements MutableBlob*/ {
/*
    @Override
    public InputStream openInputStream() throws IOException {
        if (BlobType.DIRECTORY.equals(this.getType())) {
            throw new IOException("The blob is a directory");
        }
        if (Objects.nonNull(this.getFile())) {
            return FileUtils.openInputStream(this.getFile());
        }
        if (Objects.nonNull(this.getUrl())) {
            return new UrlResource(this.getUrl()).getInputStream();
        }
        throw new IOException("The blob has no stream");
    }

    @Override
    public void createFile() {
        this.setType(BlobType.FILE);
    }

    @Override
    public void makeDirectory() {
        this.setType(BlobType.DIRECTORY);
    }

    @Override
    public void rename(String name) {
        this.setName(name);
    }*/
}
