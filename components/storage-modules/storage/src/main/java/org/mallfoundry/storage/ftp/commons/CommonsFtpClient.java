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

package org.mallfoundry.storage.ftp.commons;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.mallfoundry.storage.ftp.FtpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class CommonsFtpClient implements FtpClient {

    private final FTPClient client;

    public CommonsFtpClient() {
        this.client = new FTPClient();
        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
    }

    @Override
    public int getReplyCode() {
        return this.client.getReplyCode();
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
    public void enterLocalPassiveMode() {
        this.client.enterLocalPassiveMode();
    }

    @Override
    public String printWorkingDirectory() throws IOException {
        return this.client.printWorkingDirectory();
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
    public boolean setFileType(int fileType) throws IOException {
        return this.client.setFileType(fileType);
    }

    @Override
    public boolean makeDirectory(String pathname) throws IOException {
        return this.client.makeDirectory(pathname);
    }

    @Override
    public void close() throws IOException {
        if (!client.isConnected()) {
            client.disconnect();
        }
    }
}
