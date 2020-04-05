package com.mallfoundry.storage;

import java.util.List;

public interface IndexBlobRepository {

    List<IndexBlob> findAllByBucketAndPath(String bucket, String path);

    List<IndexBlob> findAllByBucketAndPaths(String bucket, List<String> path);

    <S extends IndexBlob> List<S> saveAll(Iterable<S> indexBlobs);

    void deleteAllByBucketAndPaths(String bucket, List<String> paths);
}
