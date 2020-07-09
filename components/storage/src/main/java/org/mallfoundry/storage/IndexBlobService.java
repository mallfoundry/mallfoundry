/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.storage;

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
