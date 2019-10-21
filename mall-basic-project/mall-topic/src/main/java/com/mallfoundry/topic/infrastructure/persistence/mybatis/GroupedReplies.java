package com.mallfoundry.topic.infrastructure.persistence.mybatis;

import com.mallfoundry.topic.domain.ReplyComment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GroupedReplies {

    @Getter
    @Setter
    private String commentId;

    @Setter
    @Getter
    private List<ReplyComment> replies;
}
