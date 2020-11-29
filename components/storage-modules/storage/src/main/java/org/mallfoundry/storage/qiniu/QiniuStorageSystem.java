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

package org.mallfoundry.storage.qiniu;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Setter;
import org.mallfoundry.storage.AbstractStorageSystem;
import org.mallfoundry.storage.BlobResource;
import org.mallfoundry.storage.BlobType;
import org.mallfoundry.storage.StorageException;
import org.mallfoundry.util.PathUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;

public class QiniuStorageSystem extends AbstractStorageSystem implements InitializingBean {

    @Setter
    private String accessKey;

    @Setter
    private String secretKey;

    @Setter
    private String region;

    @Setter
    private String bucket;

    private Auth auth;

    private UploadManager manager;

    public QiniuStorageSystem(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.auth = Auth.create(this.accessKey, this.secretKey);
        this.manager = new UploadManager(new Configuration(this.getRegionInstance()));
    }

    @Override
    public void store(BlobResource resource, String pathname) throws IOException {
        if (BlobType.FILE.equals(resource.getType())) {
            var token = auth.uploadToken(this.bucket);
            var response = manager.put(resource.toFile(), PathUtils.removePrefixSeparator(pathname), token);
            if (response.isServerError()) {
                throw new StorageException(response.error);
            }
        }
    }

    private Region getRegionInstance() {
        var regionMethod = ReflectionUtils.findMethod(Region.class, this.region);
        Assert.notNull(regionMethod, String.format("Region(%s) does not exist.", this.region));
        return (Region) ReflectionUtils.invokeMethod(regionMethod, null);
    }

}
