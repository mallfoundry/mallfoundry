package org.mallfoundry.storage.ftp;

import java.io.Serializable;

public interface FtpFile extends Serializable {
    String getName();

    boolean isDirectory();

    boolean isFile();
}
