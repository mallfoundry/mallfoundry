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

package com.mallfoundry.topic.jdbc;

import com.mallfoundry.data.PagedList;
import com.mallfoundry.topic.Comment;
import com.mallfoundry.topic.CommentRepository;
import com.mallfoundry.topic.ReplyComment;
import com.mallfoundry.topic.ReplyCommentRepository;
import com.mallfoundry.topic.Topic;
import com.mallfoundry.topic.TopicRepository;
import com.mallfoundry.topic.TopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JdbcTopicService implements TopicService {

    private final TopicRepository topicRepository;

    private final CommentRepository commentRepository;

    private final ReplyCommentRepository replyCommentRepository;

    public JdbcTopicService(TopicRepository topicRepository,
                            CommentRepository commentRepository,
                            ReplyCommentRepository replyCommentRepository) {
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
        this.replyCommentRepository = replyCommentRepository;
    }

    @Override
    public Topic getTopic(String topicName) {
        return this.topicRepository.findByName(topicName);
    }

    @Transactional
    @Override
    public void createTopic(Topic topic) {
        this.topicRepository.save(topic);
    }

    @Transactional
    @Override
    public void deleteTopic(String topicName) {
        this.topicRepository.delete(topicName);
        this.commentRepository.deleteByTopicName(topicName);
    }

    @Transactional
    @Override
    public void addComment(Comment comment) {
        Topic topic = this.topicRepository.findByName(comment.getTopicName());
        topic.incrementComments();
        this.topicRepository.updateComments(topic);
        this.commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        this.commentRepository.delete(comment.getId());
    }

    @Override
    public PagedList<Comment> getComments(String topicName, int offset, int limit) {
        return this.commentRepository.findListByTopicName(topicName, offset, limit);
    }

    @Override
    public void addReply(ReplyComment reply) {
        this.replyCommentRepository.save(reply);
    }

    @Override
    public void deleteReply(ReplyComment reply) {
        this.replyCommentRepository.delete(reply.getReplyId());
    }
}
