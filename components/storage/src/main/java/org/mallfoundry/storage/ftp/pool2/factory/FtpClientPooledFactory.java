package org.mallfoundry.storage.ftp.pool2.factory;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.mallfoundry.storage.ftp.FtpClient;
import org.mallfoundry.storage.ftp.FtpClientFactory;
import org.mallfoundry.storage.ftp.pool2.DelegatingFtpClient;
import org.springframework.dao.DataAccessResourceFailureException;

public class FtpClientPooledFactory implements FtpClientFactory {

    private final FtpClientPooledConfiguration configuration;

    private final GenericObjectPool<FtpClient> clientPool;

    public FtpClientPooledFactory(FtpClientPooledConfiguration configuration) {
        this.configuration = configuration;
        var factory = new FtpClientPooledObjectFactory(configuration);
        this.clientPool = new GenericObjectPool<>(factory);
    }

    @Override
    public FtpClient getClient() {
        final FtpClient client;
        try {
            client = this.clientPool.borrowObject();
        } catch (Exception e) {
            throw new DataAccessResourceFailureException("Failed to borrow ftp client from pool.", e);
        }
        return new DelegatingFtpClient(clientPool, client);
    }
}
