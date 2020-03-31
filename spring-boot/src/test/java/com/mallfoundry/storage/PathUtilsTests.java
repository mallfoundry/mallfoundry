/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.storage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class PathUtilsTests {

    public static void main(String[] args) {
        String filename = "/a/b/c/d/";
        System.out.println(FilenameUtils.getPath(filename));
        System.out.println(FilenameUtils.getPathNoEndSeparator(filename));
        System.out.println(FilenameUtils.getFullPath(filename));
        System.out.println(FilenameUtils.getFullPathNoEndSeparator(filename));
        System.out.println(FilenameUtils.normalize(filename, true));

//        FilenameUtils.normalizeNoEndSeparator()
    }
}
