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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_storage_shared_blob")
public class SharedBlob {

    @Id
    @Column(name = "url_")
    private String url;

    @Column(name = "size_")
    private long size;

    @Column(name = "md5_")
    private String md5;

    @Column(name = "crc32_")
    private String crc32;

    public SharedBlob(File file) throws IOException {
        this.size = file.length();
        this.md5 = md5Hex(file);
        this.crc32 = crc32Hex(file);
    }

    public static SharedBlob of(Blob blob) throws IOException {
        return new SharedBlob(blob.toFile());
    }

    private static String md5Hex(File file) throws IOException {
        try (var inputStream = FileUtils.openInputStream(file)) {
            return DigestUtils.md5Hex(inputStream);
        }
    }

    private static String crc32Hex(File file) throws IOException {
        return Long.toHexString(FileUtils.checksumCRC32(file));
    }
}
