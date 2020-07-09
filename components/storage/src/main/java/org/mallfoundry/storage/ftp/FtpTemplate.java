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
    public String printWorkingDirectory() throws FtpException {
        return this.execute(FtpClient::printWorkingDirectory);
    }

    @Override
    public boolean changeWorkingDirectory(String pathname) throws FtpException {
        return this.execute(client -> client.changeWorkingDirectory(pathname));
    }

    @Override
    public void enterLocalPassiveMode() {
        this.execute(client -> {
            client.enterLocalPassiveMode();
            return null;
        });
    }

    @Override
    public boolean setFileType(int fileType) throws FtpException {
        return this.execute(client -> client.setFileType(fileType));
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
