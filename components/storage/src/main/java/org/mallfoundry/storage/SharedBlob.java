/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

    @Column(name = "path_")
    private String path;

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
