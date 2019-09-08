/*
 * Copyright 2019 the original author or authors.
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

package com.github.shop.fs;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class PathTests {

    @Test
    public void testGetParentPath() throws Exception {

        String path = "a/b/c/d";
        String expectParentPath = "a/b/c";
        String parentPath = FilePathUtils.getParent(path);
        System.out.println(parentPath);
        Assert.isTrue(expectParentPath.equals(parentPath), "error");
    }

    @Test
    public void testJoinPath() {
        String joinFile = FilePathUtils.join("/ab/c/sss", "/ssa", "/ddd");
        System.out.println(joinFile);
    }
}
