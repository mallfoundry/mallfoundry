package org.mallfoundry.storage.ftp;

import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.storage.AbstractStorageSystem;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.util.PathUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FtpStorageSystem extends AbstractStorageSystem implements InitializingBean {

    @Setter
    private String baseDirectory;

    @Setter
    private FtpTemplate ftpTemplate;

    public FtpStorageSystem(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.ftpTemplate, "Property 'ftpTemplate' is required");
    }

    @Override
    public void storeBlobToPath(Blob blob, String pathname) throws IOException {
        this.makeParentDirectory(pathname);
        try (var stream = blob.openInputStream()) {
            if (!this.storeFile(pathname, stream)) {
                throw new IOException("Upload failed");
            }
        }
        blob.setUrl(this.concatAccessUrl(pathname));
        blob.setSize(blob.toFile().length());
    }

    private boolean storeFile(String pathname, InputStream local) {
        this.ftpTemplate.enterLocalPassiveMode();
        this.ftpTemplate.setFileType(FtpClient.BINARY_FILE_TYPE);
        return this.ftpTemplate.storeFile(this.getStorePathname(pathname), local);
    }

    private String getStorePathname(String pathname) {
        return Objects.isNull(this.baseDirectory) ? PathUtils.normalize(pathname)
                : PathUtils.concat(this.baseDirectory, pathname);
    }

    private String getWorkingDirectory(String directory) {
        return Objects.isNull(this.baseDirectory) ? PathUtils.normalize(directory)
                : PathUtils.concat(this.baseDirectory, directory);
    }

    private boolean changeWorkingDirectory(String directory) {
        return this.ftpTemplate.changeWorkingDirectory(directory);
    }

    private List<String> getDirectoryParents(String pathname) {
        var paths = new ArrayList<String>();
        var parentPath = pathname;
        while (StringUtils.isNotEmpty(parentPath)) {
            parentPath = FilenameUtils.getFullPathNoEndSeparator(parentPath);
            if (PathUtils.ROOT_PATH.equals(parentPath)) {
                break;
            }
            paths.add(parentPath);
        }
        Collections.reverse(paths);
        return Collections.unmodifiableList(paths);
    }

    private void makeParentDirectory(String pathname) throws IOException {
        var currentWorkingDirectory = this.ftpTemplate.printWorkingDirectory();
        try {
            if (this.changeWorkingDirectory(FilenameUtils.getFullPathNoEndSeparator(pathname))) {
                return;
            }
            var pathParents = getDirectoryParents(pathname);
            for (var directory : pathParents) {
                var workingDirectory = this.getWorkingDirectory(directory);
                if (!this.changeWorkingDirectory(workingDirectory)) {
                    if (!this.ftpTemplate.makeDirectory(workingDirectory)) {
                        throw new IOException("Failed to create directory");
                    }
                }
            }
        } finally {
            this.changeWorkingDirectory(currentWorkingDirectory);
        }
    }
}
