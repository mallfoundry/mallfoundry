package org.mallfoundry.storage.aliyun;

import com.aliyun.oss.OSS;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.storage.AbstractStorageSystem;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.StorageException;
import org.mallfoundry.util.PathUtils;
import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.util.Objects;

public class AliyunStorageSystem extends AbstractStorageSystem implements DisposableBean {

    private final OSS client;

    private String bucketName;

    public AliyunStorageSystem(OSS client, String bucketName, String baseUrl) {
        super(baseUrl);
        this.client = client;
        this.setBucketName(bucketName);
    }

    public void setBucketName(String bucketName) {
        if (StringUtils.isBlank(bucketName)) {
            throw new StorageException("The bucket name must not be empty");
        }
        this.bucketName = StringUtils.trim(bucketName);
    }

    @Override
    public void storeBlobToPath(Blob blob, String path) throws IOException {
        if (blob.isFile()) {
            var file = blob.toFile();
            this.client.putObject(this.bucketName, PathUtils.removePrefixSeparator(path), file);
            blob.setUrl(this.concatAccessUrl(path));
            blob.setSize(file.length());
        }
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(this.client)) {
            this.client.shutdown();
        }
    }
}
