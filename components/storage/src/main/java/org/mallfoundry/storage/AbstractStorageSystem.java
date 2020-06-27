package org.mallfoundry.storage;

import lombok.Setter;

import java.io.IOException;

public abstract class AbstractStorageSystem implements StorageSystem {

    @Setter
    private StoragePathReplacer pathReplacer;

    @Override
    public void storeBlob(Blob blob) throws IOException {
        this.storeBlobToPath(blob, "");
    }

    public abstract void storeBlobToPath(Blob blob, String path) throws IOException;
}
