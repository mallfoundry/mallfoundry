package org.mallfoundry.storage.ftp;

import java.io.InputStream;

public interface FtpOperations {

    <T> T execute(FtpClientCallback<T> action) throws FtpException;

    String printWorkingDirectory() throws FtpException;

    boolean changeWorkingDirectory(String pathname) throws FtpException;

    void enterLocalPassiveMode();

    boolean setFileType(int fileType) throws FtpException;

    boolean storeFile(String name, InputStream local) throws FtpException;

    boolean makeDirectory(String pathname) throws FtpException;
}
