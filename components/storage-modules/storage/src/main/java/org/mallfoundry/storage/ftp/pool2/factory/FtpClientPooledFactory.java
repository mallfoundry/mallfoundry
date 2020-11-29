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

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.mallfoundry.storage.ftp.FtpClient;
import org.mallfoundry.storage.ftp.FtpClientFactory;
import org.mallfoundry.storage.ftp.pool2.DelegatingFtpClient;
import org.springframework.dao.DataAccessResourceFailureException;

public class FtpClientPooledFactory implements FtpClientFactory {

    private final GenericObjectPool<FtpClient> clientPool;

    public FtpClientPooledFactory(FtpClientPooledConfiguration configuration) throws ClassNotFoundException {
        var factory = new FtpClientPooledObjectFactory(configuration);
        this.clientPool = new GenericObjectPool<>(factory);
        this.clientPool.setMaxTotal(configuration.getMaxTotal());
        this.clientPool.setMaxIdle(configuration.getMaxIdle());
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
