package com.mallfoundry.topic.infrastructure.persistence.mybatis;

import com.mallfoundry.topic.ReplyComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Mapper
public interface ReplyCommentMapper {

    void insertReplyComment(ReplyComment reply);

    void deleteReplyComment(@Param("replyId") String replyId);

    List<GroupedReplies> selectListByCommentIds(@Param("commentIds") Set<String> commentIds);
}
