package com.mallfoundry.topic.infrastructure.persistence.mybatis;

import com.mallfoundry.topic.domain.ReplyComment;
import com.mallfoundry.topic.domain.ReplyCommentRepository;
import com.mallfoundry.topic.domain.TopicIdentifier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyCommentRepositoryMybatis implements ReplyCommentRepository {

    private ReplyCommentMapper replyCommentMapper;

    public ReplyCommentRepositoryMybatis(ReplyCommentMapper replyCommentMapper) {
        this.replyCommentMapper = replyCommentMapper;
    }

    @Override
    public void save(ReplyComment reply) {
        // Set reply id.
        if (StringUtils.isEmpty(reply.getId())) {
            reply.setId(TopicIdentifier.newReplyId());
        }
        this.replyCommentMapper.insertReplyComment(reply);
    }

    @Override
    public void delete(String replyId) {
        this.replyCommentMapper.deleteReplyComment(replyId);
    }
}
