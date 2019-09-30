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

package com.mallfoundry.topic.infrastructure.persistence.mybatis;

import com.mallfoundry.data.OffsetPagedList;
import com.mallfoundry.data.PagedList;
import com.mallfoundry.topic.Comment;
import com.mallfoundry.topic.CommentRepository;
import com.mallfoundry.topic.TopicIdentifier;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CommentRepositoryMybatis implements CommentRepository {

    private final CommentMapper commentMapper;

    private final ReplyCommentMapper replyCommentMapper;

    public CommentRepositoryMybatis(CommentMapper commentMapper,
                                    ReplyCommentMapper replyCommentMapper) {
        this.commentMapper = commentMapper;
        this.replyCommentMapper = replyCommentMapper;
    }

    @Override
    public void save(Comment comment) {
        // Set comment id.
        if (StringUtils.isEmpty(comment.getId())) {
            comment.setId(TopicIdentifier.newCommentId());
        }
        this.commentMapper.insertComment(comment);
    }

    @Override
    public void delete(String commentId) {
        this.commentMapper.deleteComment(commentId);
    }

    @Override
    public void deleteByTopicName(String topicName) {
        this.commentMapper.deleteCommentsByTopicName(topicName);
    }

    @Override
    public PagedList<Comment> findListByTopicName(String topicName, int offset, int limit) {
        long total = this.commentMapper.totalCount();
        if (total == 0) {
            return PagedList.empty();
        }
        List<Comment> comments = this.commentMapper.selectCommentsByTopicName(topicName, offset, limit);
        Set<String> commentIdSet = comments.stream().map(Comment::getId).collect(Collectors.toSet());
        List<GroupedReplies> replies = replyCommentMapper.selectListByCommentIds(commentIdSet);
        comments.forEach(comment -> {
            GroupedReplies groupedReplies = IterableUtils.find(replies, gr -> StringUtils.equals(gr.getCommentId(), comment.getId()));
            comment.setReplies(Objects.nonNull(groupedReplies) ? groupedReplies.getReplies() : Collections.emptyList());
        });
        return OffsetPagedList.of(comments, offset, limit, total);
    }
}
