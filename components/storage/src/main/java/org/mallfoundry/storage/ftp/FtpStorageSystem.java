package org.mallfoundry.storage.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.StorageException;
import org.mallfoundry.storage.StorageSystem;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

public class FtpStorageSystem implements StorageSystem, InitializingBean, DisposableBean {

    private FTPClient ftpClient;

    private final FtpConfiguration configuration;

    public FtpStorageSystem(FtpConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.ftpClient = new FTPClient();
        if (!ftpClient.isConnected()) {
            ftpClient.connect(this.configuration.getHostname(), this.configuration.getPort());
        }
        ftpClient.login(this.configuration.getUsername(), this.configuration.getPassword());
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            throw new StorageException("Connection failed");
        }
    }

    @Override
    public void storeBlob(Blob blob) throws IOException {
        // TODO
        this.ftpClient.storeFile(blob.getName(), blob.openInputStream());
    }

    @Override
    public void destroy() throws Exception {
        if (this.ftpClient.isConnected()) {
            this.ftpClient.disconnect();
        }
    }

}
