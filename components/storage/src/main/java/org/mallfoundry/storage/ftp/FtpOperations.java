package org.mallfoundry.storage.ftp;

import java.io.InputStream;

public interface FtpOperations {

    <T> T execute(FtpClientCallback<T> action) throws FtpException;

    boolean changeWorkingDirectory(String pathname) throws FtpException;

    boolean storeFile(String name, InputStream local) throws FtpException;

    boolean makeDirectory(String pathname) throws FtpException;
}
