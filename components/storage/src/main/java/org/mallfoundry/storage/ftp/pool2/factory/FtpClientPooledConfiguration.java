package org.mallfoundry.storage.ftp.pool2.factory;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.storage.ftp.FtpClientConfiguration;

@Getter
@Setter
public class FtpClientPooledConfiguration extends FtpClientConfiguration {
    private String clientClassName;
}
