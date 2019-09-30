package com.mallfoundry.topic;

public interface ReplyCommentRepository {

    void save(ReplyComment reply);

    void delete(String replyId);
}
