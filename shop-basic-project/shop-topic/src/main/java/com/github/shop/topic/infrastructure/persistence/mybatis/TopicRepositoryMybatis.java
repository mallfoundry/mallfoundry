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

package com.github.shop.topic.infrastructure.persistence.mybatis;

import com.github.shop.topic.Topic;
import com.github.shop.topic.TopicRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TopicRepositoryMybatis implements TopicRepository {

    private final TopicMapper topicMapper;

    public TopicRepositoryMybatis(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    @Override
    public Topic findByName(String topicName) {
        return this.topicMapper.selectTopicByName(topicName);
    }

    @Override
    public void save(Topic topic) {
        this.topicMapper.insertTopic(topic);
    }

    @Override
    public void updateComments(Topic topic) {
        this.update(Topic.ofNameAndComments(topic.getName(), topic.getComments()));
    }

    @Override
    public void update(Topic topic) {
        topicMapper.updateTopic(topic);
    }

    @Override
    public void delete(String topicName) {
        this.topicMapper.deleteTopicByName(topicName);
    }
}
