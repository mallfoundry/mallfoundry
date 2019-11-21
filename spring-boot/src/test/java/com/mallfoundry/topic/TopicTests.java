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

package com.mallfoundry.topic;

import com.mallfoundry.data.PagedList;
import com.mallfoundry.topic.domain.Comment;
import com.mallfoundry.topic.domain.Topic;
import com.mallfoundry.topic.application.TopicService;
import com.mallfoundry.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TopicTests {

    @Autowired
    private TopicService topicService;

    @Test
    @Rollback(false)
    public void testCreateTopic() {
        Topic topic = new Topic();
        topic.setName("topic_1");
        topic.setType("TEST");
        topic.setCreateTime(new Date());
        topicService.createTopic(topic);
    }

    @Test
    public void testGetTopic() {
        Topic topic = this.topicService.getTopic("topic_1");
        System.out.println(topic);
    }

    @Test
    public void testDeleteTopic() {
        this.topicService.deleteTopic("topic_1");
    }

    @Test
    @Rollback(false)
    public void testAddComment() {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setTopicName("topic_1");
        comment.setUid("10001");
        comment.setNickname("nike name1");
        comment.setMessage("你就是刚");
        comment.setCreateTime(new Date());
        topicService.addComment(comment);
    }


    @Test
    @Rollback(false)
    public void testAddComments() throws Exception {
        for (int i = 0; i < 100; i++) {
            Comment comment = new Comment();
            comment.setId(String.valueOf(i));
            comment.setTopicName("topic_1");
            comment.setUid("10001");
            comment.setNickname("nike name1");
            comment.setMessage("你就是刚");
            Thread.sleep(1000);
            comment.setCreateTime(new Date());
            topicService.addComment(comment);
        }
    }

    @Test
    public void testDeleteComment() {
        this.topicService.deleteComment(Comment.of("topic_1", "topic_1"));
    }


    @Test
    public void testGetComments() {
        PagedList<Comment> comments = this.topicService.getComments("topic_1", 0, 10);
        System.out.println(JsonUtils.stringify(comments));
    }
}
