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
