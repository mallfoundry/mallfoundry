package com.mallfoundry.storage.repository.jpa;

import com.mallfoundry.storage.IndexBlob;
import com.mallfoundry.storage.IndexBlobId;
import com.mallfoundry.storage.IndexBlobRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaIndexBlobRepository
        extends IndexBlobRepository, JpaRepository<IndexBlob, IndexBlobId> {

    @Query("from IndexBlob where id.bucket = ?1 and (id.path = ?2 or id.value = ?2)")
    @Override
    List<IndexBlob> findAllByBucketAndPath(String bucket, String path);

    @Modifying
    @Query("delete from IndexBlob where id.bucket = ?1 and id.path in (?2)")
    @Override
    void deleteAllByBucketAndPaths(String bucket, List<String> paths);
}
