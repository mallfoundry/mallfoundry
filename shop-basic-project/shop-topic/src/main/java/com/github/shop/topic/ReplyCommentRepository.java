package com.github.shop.topic;

public interface ReplyCommentRepository {

    void save(ReplyComment reply);

    void delete(String replyId);
}
