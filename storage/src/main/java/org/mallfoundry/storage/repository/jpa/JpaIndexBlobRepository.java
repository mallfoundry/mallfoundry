package org.mallfoundry.storage.repository.jpa;

import org.mallfoundry.storage.IndexBlob;
import org.mallfoundry.storage.IndexBlobId;
import org.mallfoundry.storage.IndexBlobRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaIndexBlobRepository
        extends IndexBlobRepository, JpaRepository<IndexBlob, IndexBlobId> {

    @Query("from IndexBlob where id.bucket = ?1 and (id.path = ?2 or id.value = ?2)")
    @Override
    List<IndexBlob> findAllByBucketAndPath(String bucket, String path);

    @Query("from IndexBlob where id.bucket = ?1 and (id.path in (?2) or id.value in (?2))")
    @Override
    List<IndexBlob> findAllByBucketAndPaths(String bucket, List<String> path);

    @Modifying
    @Query("delete from IndexBlob where id.bucket = ?1 and id.path in (?2)")
    @Override
    void deleteAllByBucketAndPaths(String bucket, List<String> paths);
}
