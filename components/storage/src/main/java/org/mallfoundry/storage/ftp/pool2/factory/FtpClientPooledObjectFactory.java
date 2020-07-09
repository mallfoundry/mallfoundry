/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
