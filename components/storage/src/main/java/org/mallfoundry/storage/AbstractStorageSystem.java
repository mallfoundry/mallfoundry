package org.mallfoundry.storage;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractStorageSystem implements StorageSystem {

    private String baseUrl;

    @Getter
    @Setter
    private String baseDirectory;

    @Setter
    private StoragePathReplacer pathReplacer;

    public void setBaseUrl(String baseUrl) {
        if (StringUtils.isBlank(baseUrl)) {
            throw new StorageException("The base url must not be empty");
        }
        this.baseUrl = Optional.of(baseUrl)
                .map(String::trim)
                .map(s -> s.endsWith("/") ? s : s + "/")
                .orElseThrow();
    }

    public AbstractStorageSystem(String baseUrl) {
        this.setBaseUrl(baseUrl);
    }

    @Override
    public void storeBlob(Blob blob) throws IOException {
        this.storeBlobToPath(blob, this.getStorePath(blob));
    }

    protected String getStorePath(Blob blob) throws IOException {
        return Objects.isNull(this.pathReplacer)
                ? PathUtils.concat(blob.getBucket(), blob.getPath())
                : this.pathReplacer.replace(blob.toFile());
    }

    protected String concatAccessUrl(String path) {
        var url = path.startsWith("/") ? path.substring(1) : path;
        return String.join("", this.baseUrl, url);
    }

    public abstract void storeBlobToPath(Blob blob, String pathname) throws IOException;
}
