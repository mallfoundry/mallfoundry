package org.mallfoundry.storage.ftp.commons;

import org.apache.commons.net.ftp.FTPClient;
import org.mallfoundry.storage.ftp.FtpClient;

import java.io.IOException;
import java.io.InputStream;

public class CommonsFtpClient implements FtpClient {

    private final FTPClient client;

    public CommonsFtpClient(FTPClient client) {
        this.client = client;
    }

    @Override
    public void connect(String hostname, int port) throws IOException {
        this.client.connect(hostname, port);
    }

    @Override
    public boolean isConnected() {
        return this.client.isConnected();
    }

    @Override
    public boolean login(String username, String password) throws IOException {
        return this.client.login(username, password);
    }

    @Override
    public boolean changeWorkingDirectory(String pathname) throws IOException {
        return this.client.changeWorkingDirectory(pathname);
    }

    @Override
    public boolean storeFile(String name, InputStream local) throws IOException {
        return this.client.storeFile(name, local);
    }

    @Override
    public boolean makeDirectory(String pathname) throws IOException {
        return this.client.makeDirectory(pathname);
    }
}
