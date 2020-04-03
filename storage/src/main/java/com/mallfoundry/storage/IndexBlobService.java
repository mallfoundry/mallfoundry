package com.mallfoundry.storage;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
//                .flatMap(index -> Stream.of(index.getPath(), index.getValue()))
                .distinct()
                .collect(Collectors.toList());
    }

    public void buildIndexes(String bucket, String path) {
        this.indexBlobRepository.saveAll(IndexBlob.of(bucket, path));
    }

    public void deleteIndexes(String bucket, String path) {
        this.indexBlobRepository.deleteAllByBucketAndPaths(bucket, this.getIndexes(bucket, path));
    }
}
