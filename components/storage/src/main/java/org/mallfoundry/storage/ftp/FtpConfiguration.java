package org.mallfoundry.storage.ftp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FtpConfiguration {
    private String hostname;
    private int port = 21;
    private String username;
    private String password;
    private String controlEncoding = "utf-8";
}
