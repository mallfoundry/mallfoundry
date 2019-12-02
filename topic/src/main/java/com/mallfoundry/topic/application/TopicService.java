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

package com.mallfoundry.topic.application;

import com.mallfoundry.data.OffsetList;
import com.mallfoundry.topic.domain.Comment;
import com.mallfoundry.topic.domain.CommentRepository;
import com.mallfoundry.topic.domain.ReplyComment;
import com.mallfoundry.topic.domain.ReplyCommentRepository;
import com.mallfoundry.topic.domain.Topic;
import com.mallfoundry.topic.domain.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    private final CommentRepository commentRepository;

    private final ReplyCommentRepository replyCommentRepository;

    public TopicService(TopicRepository topicRepository,
                        CommentRepository commentRepository,
                        ReplyCommentRepository replyCommentRepository) {
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
        this.replyCommentRepository = replyCommentRepository;
    }

    public Topic getTopic(String topicName) {
        return this.topicRepository.findByName(topicName);
    }

    @Transactional
    public void createTopic(Topic topic) {
        this.topicRepository.save(topic);
    }

    @Transactional
    public void deleteTopic(String topicName) {
        this.topicRepository.delete(topicName);
        this.commentRepository.deleteByTopicName(topicName);
    }

    @Transactional
    public void addComment(Comment comment) {
        Topic topic = this.topicRepository.findByName(comment.getTopicName());
        topic.incrementComments();
        this.topicRepository.updateComments(topic);
        this.commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        this.commentRepository.delete(comment.getId());
    }

    public OffsetList<Comment> getComments(String topicName, int offset, int limit) {
        return this.commentRepository.findListByTopicName(topicName, offset, limit);
    }

    public void addReply(ReplyComment reply) {
        this.replyCommentRepository.save(reply);
    }

    public void deleteReply(ReplyComment reply) {
        this.replyCommentRepository.delete(reply.getReplyId());
    }
}
