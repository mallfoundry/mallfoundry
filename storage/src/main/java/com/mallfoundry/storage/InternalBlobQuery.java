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

import com.mallfoundry.data.PageLimitSupport;
import com.mallfoundry.storage.BlobQuery;
import com.mallfoundry.storage.BlobType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalBlobQuery extends PageLimitSupport implements BlobQuery {

    private String bucket;

    private String path;

    private BlobType type;

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }
}
