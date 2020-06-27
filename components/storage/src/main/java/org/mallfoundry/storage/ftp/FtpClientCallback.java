package org.mallfoundry.storage.ftp;

import java.io.IOException;

public interface FtpClientCallback<T> {
    T doInClient(FtpClient client) throws FtpException, IOException;
}
