package org.mallfoundry.storage.ftp;

import java.io.InputStream;

public class FtpTemplate implements FtpOperations {

    private final FtpClientFactory clientFactory;

    public FtpTemplate(FtpClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public <T> T execute(FtpClientCallback<T> action) throws FtpException {
        try (var client = this.clientFactory.getClient()) {
            return action.doInClient(client);
        } catch (Exception e) {
            throw new FtpException(e);
        }
    }

    @Override
    public boolean changeWorkingDirectory(String pathname) throws FtpException {
        return this.execute(client -> client.changeWorkingDirectory(pathname));
    }

    @Override
    public boolean storeFile(String name, InputStream local) throws FtpException {
        return this.execute(client -> client.storeFile(name, local));
    }

    @Override
    public boolean makeDirectory(String pathname) throws FtpException {
        return this.execute(client -> client.makeDirectory(pathname));
    }
}
