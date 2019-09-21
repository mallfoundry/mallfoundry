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

package com.github.shop.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Discuss a topic.
 */
public class Topic {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private int comments;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("create_time")
    private Date createTime;

    /**
     * Increments the number of comments for the current topic.
     */
    public void incrementComments() {
        this.comments++;
    }

    /**
     * Create a new topic object, set name and comments properties.
     *
     * @param name     topic name
     * @param comments topic comments
     * @return a new topic object
     */
    public static Topic ofNameAndComments(String name, int comments) {
        Topic topic1 = new Topic();
        topic1.setName(name);
        topic1.setComments(comments);
        return topic1;
    }
}
