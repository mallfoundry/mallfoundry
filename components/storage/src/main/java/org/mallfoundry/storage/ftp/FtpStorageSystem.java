package org.mallfoundry.storage.ftp;

import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.mallfoundry.storage.AbstractStorageSystem;
import org.mallfoundry.storage.Blob;

import java.io.IOException;
import java.io.InputStream;

public class FtpStorageSystem extends AbstractStorageSystem {

    @Setter
    private String baseDirectory;

    @Setter
    private String baseUrl;

    private final FtpTemplate ftpTemplate;

    public FtpStorageSystem(FtpTemplate ftpTemplate) {
        this.ftpTemplate = ftpTemplate;
    }

    @Override
    public void storeBlobToPath(Blob blob, String pathname) throws IOException {
        var directory = FilenameUtils.getPathNoEndSeparator(pathname);
        if (!this.changeWorkingDirectory(directory)) {
            if (!this.ftpTemplate.makeDirectory(directory)) {
                throw new IOException("Failed to create directory");
            }
        }

        try (var stream = blob.openInputStream()) {
            if (!this.storeFile(pathname, stream)) {
                throw new IOException("Upload failed");
            }
        }
        blob.setUrl(this.getAccessUrl(pathname));
    }

    private boolean storeFile(String pathname, InputStream local) {
        return this.ftpTemplate.storeFile(
                FilenameUtils.concat(this.baseDirectory, pathname), local);
    }

    private boolean changeWorkingDirectory(String directory) {
        return this.ftpTemplate.changeWorkingDirectory(
                FilenameUtils.concat(this.baseDirectory, directory));
    }

    private String getAccessUrl(String pathname) {
        return this.baseUrl + pathname;
    }
}
