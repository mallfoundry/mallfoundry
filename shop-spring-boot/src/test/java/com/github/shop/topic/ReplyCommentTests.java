package com.github.shop.topic;

import com.github.shop.topic.infrastructure.persistence.mybatis.GroupedReplies;
import com.github.shop.topic.infrastructure.persistence.mybatis.ReplyCommentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ReplyCommentTests {

    @Autowired
    private ReplyCommentMapper replyCommentMapper;

    @Test
    public void testSelectGroupedReplies() {
        List<GroupedReplies> replies = replyCommentMapper.selectListByCommentIds(Set.of("98", "09", "32"));
        System.out.println(replies);
    }
}
