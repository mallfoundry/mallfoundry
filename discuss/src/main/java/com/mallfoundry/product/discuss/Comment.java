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

package com.mallfoundry.product.discuss;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"topicName", "id", "uid", "nickname", "message", "likes", "createTime"})
public class Comment {

    @JsonProperty("topic_name")
    private String topicName;

    private String id;

    private String uid;

    private String nickname;

    private String message;

    private int likes;

    private List<ReplyComment> replies;

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
