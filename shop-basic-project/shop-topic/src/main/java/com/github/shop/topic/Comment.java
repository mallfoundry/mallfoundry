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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@JsonPropertyOrder({"topicName", "id", "uid", "nickname", "message", "likes", "createTime"})
public class Comment {

    @Getter
    @Setter
    @JsonProperty("topic_name")
    private String topicName;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String uid;

    @Getter
    @Setter
    private String nickname;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private int likes;

    @Getter
    @Setter
    private List<ReplyComment> replies;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("create_time")
    private Date createTime;


    public static Comment of(String topicName, String commentId) {
        Comment comment = new Comment();
        comment.setTopicName(topicName);
        comment.setId(commentId);
        return comment;
    }
}
