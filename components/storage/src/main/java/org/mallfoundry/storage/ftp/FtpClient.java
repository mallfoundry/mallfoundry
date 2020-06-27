package org.mallfoundry.storage.ftp;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface FtpClient extends Closeable {

    int getReplyCode();

    void connect(String hostname, int port) throws IOException;

    boolean isConnected();

    boolean login(String username, String password) throws IOException;

    boolean changeWorkingDirectory(String pathname) throws IOException;

    boolean storeFile(String name, InputStream local) throws IOException;

    boolean makeDirectory(String pathname) throws IOException;
}
