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

package org.mallfoundry.storage.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Setter;
import org.mallfoundry.storage.AbstractStorageSystem;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.BlobType;
import org.mallfoundry.util.PathUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.Objects;

public class AliyunStorageSystem extends AbstractStorageSystem implements InitializingBean, DisposableBean {

    @Setter
    private String accessKeyId;

    @Setter
    private String accessKeySecret;

    @Setter
    private String endpoint;

    @Setter
    private String bucketName;

    private OSS client;

    public AliyunStorageSystem(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public void afterPropertiesSet() {
        this.client = new OSSClientBuilder().build(this.endpoint, this.accessKeyId, this.accessKeySecret);
    }

    @Override
    public void storeBlobToPath(Blob blob, String pathname) throws IOException {
        if (BlobType.FILE.equals(blob.getType())) {
            var file = blob.toFile();
            this.client.putObject(this.bucketName, PathUtils.removePrefixSeparator(pathname), file);
        }
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(this.client)) {
            this.client.shutdown();
        }
    }

}
