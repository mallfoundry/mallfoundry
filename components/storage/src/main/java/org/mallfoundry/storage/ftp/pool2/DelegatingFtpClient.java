package org.mallfoundry.storage.ftp.pool2;

import lombok.Getter;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.mallfoundry.storage.ftp.FtpClient;

import java.io.IOException;
import java.io.InputStream;

public class DelegatingFtpClient implements FtpClient {

    private final GenericObjectPool<FtpClient> clientPool;

    @Getter
    private final FtpClient delegateClient;

    public DelegatingFtpClient(GenericObjectPool<FtpClient> clientPool, FtpClient delegateClient) {
        this.clientPool = clientPool;
        this.delegateClient = delegateClient;
    }

    @Override
    public int getReplyCode() {
        return this.delegateClient.getReplyCode();
    }

    @Override
    public void connect(String hostname, int port) throws IOException {
        this.delegateClient.connect(hostname, port);
    }

    @Override
    public boolean isConnected() {
        return this.delegateClient.isConnected();
    }

    @Override
    public boolean login(String username, String password) throws IOException {
        return delegateClient.login(username, password);
    }

    @Override
    public void enterLocalPassiveMode() {
        this.delegateClient.enterLocalPassiveMode();
    }

    @Override
    public String printWorkingDirectory() throws IOException {
        return this.delegateClient.printWorkingDirectory();
    }

    @Override
    public boolean changeWorkingDirectory(String pathname) throws IOException {
        return this.delegateClient.changeWorkingDirectory(pathname);
    }

    @Override
    public boolean storeFile(String name, InputStream local) throws IOException {
        return this.delegateClient.storeFile(name, local);
    }

    @Override
    public boolean setFileType(int fileType) throws IOException {
        return this.delegateClient.setFileType(fileType);
    }

    @Override
    public boolean makeDirectory(String pathname) throws IOException {
        return this.delegateClient.makeDirectory(pathname);
    }

    @Override
    public void close() throws IOException {
        this.clientPool.returnObject(this.delegateClient);
    }
}
