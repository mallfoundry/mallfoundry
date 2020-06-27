package org.mallfoundry.storage.ftp.pool2.factory;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.mallfoundry.storage.ftp.FtpClient;
import org.mallfoundry.storage.ftp.commons.CommonsFtpClient;
import org.mallfoundry.storage.ftp.pool2.DelegatingFtpClient;
import org.springframework.beans.BeanUtils;

public class FtpClientPooledObjectFactory extends BasePooledObjectFactory<FtpClient> {

    //    private final Class<FtpClient> clientClass;
//
    private final FtpClientPooledConfiguration configuration;

    @SuppressWarnings("unchecked")
    public FtpClientPooledObjectFactory(FtpClientPooledConfiguration configuration) {
        this.configuration = configuration;
//        this.clientClass = (Class<FtpClient>) Class.forName(configuration.getClientClassName());
    }

    @Override
    public FtpClient create() throws Exception {
        return BeanUtils.instantiateClass(CommonsFtpClient.class);
    }

    @Override
    public PooledObject<FtpClient> wrap(FtpClient client) {
        return new DefaultPooledObject<>(client);
    }

    @Override
    public void destroyObject(PooledObject<FtpClient> pooledObject) throws Exception {
        var client = pooledObject.getObject();
        if (client instanceof DelegatingFtpClient) {
            ((DelegatingFtpClient) client).getDelegateClient().close();
        } else {
            client.close();
        }
    }

    @Override
    public boolean validateObject(PooledObject<FtpClient> pooledObject) {
        var client = pooledObject.getObject();
        return client.isConnected();
    }

    @Override
    public void activateObject(PooledObject<FtpClient> pooledObject) throws Exception {
        var client = pooledObject.getObject();
        if (!client.isConnected()) {
            var hostname = this.configuration.getHostname();
            var port = this.configuration.getPort();
            client.connect(hostname, port);
            /*int replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                // oops! The operation was not successful
                // for some reasons, the server refuses or
                // rejects requested operation
                return;
            }*/
        }

        var username = this.configuration.getUsername();
        var password = this.configuration.getPassword();
        if (!client.login(username, password)) {
            System.out.println("login no");
        }
    }
}
