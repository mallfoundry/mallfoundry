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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface FtpClient extends Closeable {

    int ASCII_FILE_TYPE = 0;

    int EBCDIC_FILE_TYPE = 1;

    int BINARY_FILE_TYPE = 2;

    int LOCAL_FILE_TYPE = 3;

    int getReplyCode();

    void connect(String hostname, int port) throws IOException;

    boolean isConnected();

    boolean login(String username, String password) throws IOException;

    void enterLocalPassiveMode();

    String printWorkingDirectory() throws IOException;

    boolean changeWorkingDirectory(String pathname) throws IOException;

    boolean storeFile(String name, InputStream local) throws IOException;

    boolean setFileType(int fileType) throws IOException;

    boolean makeDirectory(String pathname) throws IOException;
}
