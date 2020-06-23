package org.mallfoundry.storage.aliyun;

import com.aliyun.oss.OSS;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.StorageException;
import org.mallfoundry.storage.StoragePathReplacer;
import org.mallfoundry.storage.StorageSystem;
import org.mallfoundry.util.PathUtils;
import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AliyunStorageSystem implements StorageSystem, DisposableBean {

    private final StoragePathReplacer pathReplacer;

    private final OSS client;

    private String bucketName;

    private String baseUrl;

    public AliyunStorageSystem(OSS client, String bucketName, String baseUrl, StoragePathReplacer pathReplacer) {
        this.client = client;
        this.pathReplacer = pathReplacer;
        this.setBucketName(bucketName);
        this.setBaseUrl(baseUrl);
    }

    public void setBucketName(String bucketName) {
        if (StringUtils.isBlank(bucketName)) {
            throw new StorageException("The bucket name must not be empty");
        }
        this.bucketName = StringUtils.trim(bucketName);
    }

    public void setBaseUrl(String baseUrl) {
        if (StringUtils.isBlank(baseUrl)) {
            throw new StorageException("The base url must not be empty");
        }
        this.baseUrl = Optional.of(baseUrl)
                .map(String::trim)
                .map(s -> s.endsWith("/")
                        ? s
                        : s + "/")
                .orElseThrow();
    }

    @Override
    public void storeBlob(Blob blob) throws IOException {
        if (blob.isFile()) {
            var file = blob.toFile();
            var filePath = this.pathReplacer.replace(file);
            this.client.putObject(this.bucketName, this.removePrefixSeparator(filePath), file);
            blob.setUrl(this.concatUrl(filePath));
            blob.setSize(file.length());
        }
    }

    private String concatUrl(String path) {
        var url = path.startsWith("/")
                ? path.substring(1)
                : path;
        return String.join("", this.baseUrl, url);
    }

    private String removePrefixSeparator(String path) {
        return Optional.of(path).map(PathUtils::normalize)
                .map(s -> s.startsWith("/")
                        ? s.substring(1)
                        : s)
                .orElseThrow();
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(this.client)) {
            this.client.shutdown();
        }
    }
}
