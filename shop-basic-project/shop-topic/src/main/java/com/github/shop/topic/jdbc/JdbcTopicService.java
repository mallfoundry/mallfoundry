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

package com.github.shop.topic.jdbc;

import com.github.shop.topic.Comment;
import com.github.shop.topic.ReplyComment;
import com.github.shop.topic.Topic;
import com.github.shop.topic.TopicRepository;
import com.github.shop.topic.TopicService;
import org.springframework.stereotype.Service;

@Service
public class JdbcTopicService implements TopicService {

    private final TopicRepository topicRepository;

    public JdbcTopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void getTopic(String topicName) {

    }

    @Override
    public void createTopic(Topic topic) {
        this.topicRepository.save(topic);
    }

    @Override
    public void deleteTopic(Topic topic) {

    }

    @Override
    public void addComment(Comment comment) {

    }

    @Override
    public void addReply(ReplyComment reply) {

    }

    @Override
    public void deleteComment(Comment comment) {

    }

    @Override
    public void deleteReply(ReplyComment reply) {

    }
}
