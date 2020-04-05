package com.mallfoundry.storage;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexBlobService {

    private final IndexBlobRepository indexBlobRepository;

    public IndexBlobService(IndexBlobRepository indexBlobRepository) {
        this.indexBlobRepository = indexBlobRepository;
    }

    public List<String> getIndexes(String bucket, String path) {
        return this.indexBlobRepository
                .findAllByBucketAndPath(bucket, path)
                .stream()
                .map(IndexBlob::getPath)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getIndexes(String bucket, List<String> paths) {
        return this.indexBlobRepository
                .findAllByBucketAndPaths(bucket, paths)
                .stream()
                .map(IndexBlob::getPath)
                .distinct()
                .collect(Collectors.toList());
    }

    public void buildIndexes(String bucket, String path) {
        this.indexBlobRepository.saveAll(IndexBlob.of(bucket, path));
    }

    public void deleteIndexes(String bucket, String path) {
        this.indexBlobRepository.deleteAllByBucketAndPaths(bucket, this.getIndexes(bucket, path));
    }

    public void deleteIndexes(String bucket, List<String> paths) {
        this.indexBlobRepository.deleteAllByBucketAndPaths(bucket, this.getIndexes(bucket, paths));
    }
}
