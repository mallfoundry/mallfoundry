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

package com.mallfoundry.topic.rest;

import com.mallfoundry.data.PagedList;
import com.mallfoundry.topic.Comment;
import com.mallfoundry.topic.ReplyComment;
import com.mallfoundry.topic.Topic;
import com.mallfoundry.topic.TopicService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/basic")
@RestController
public class TopicResourceV1 {

    private final TopicService topicService;

    public TopicResourceV1(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/topics")
    public void createTopic(@RequestBody Topic topic) {
        this.topicService.createTopic(topic);
    }

    @DeleteMapping("/topics/{topic_name}")
    public void deleteTopic(@PathVariable("topic_name") String topicName) {
        this.topicService.deleteTopic(topicName);
    }

    @GetMapping("/topics/{topic_name}")
    public Topic getTopic(@PathVariable("topic_name") String topicName) {
        return this.topicService.getTopic(topicName);
    }

    @PostMapping("/topics/{topic_name}/comments")
    public void createComment(@PathVariable("topic_name") String topicName,
                              @RequestBody Comment comment) {
        comment.setTopicName(topicName);
        this.topicService.addComment(comment);
    }

    @DeleteMapping("/topics/{topic_name}/comments/{comment_id}")
    public void deleteComment(@PathVariable("topic_name") String topicName,
                              @PathVariable("comment_id") String commentId) {
        this.topicService.deleteComment(Comment.of(topicName, commentId));
    }

    @GetMapping("/topics/{topic_name}/comments")
    public PagedList<Comment> getComments(@PathVariable("topic_name") String topicName,
                                          @RequestParam(defaultValue = "0") int offset,
                                          @RequestParam(defaultValue = "10") int limit) {
        return this.topicService.getComments(topicName, offset, limit);
    }

    @PostMapping("/topics/{topic_name}/comments/{comment_id}")
    public void createReplyComment(@PathVariable("topic_name") String topicName,
                                   @PathVariable("comment_id") String commentId,
                                   @RequestBody ReplyComment reply) {
        reply.setTopicName(topicName);
        reply.setCommentId(commentId);
        this.topicService.addReply(reply);
    }

    @DeleteMapping("/topics/{topic_name}/comments/{comment_id}/replies/{reply_id}")
    public void deleteReplyComment(@PathVariable("topic_name") String topicName,
                                   @PathVariable("comment_id") String commentId,
                                   @PathVariable("reply_id") String replyId) {
        this.topicService.deleteReply(ReplyComment.of(topicName, commentId, replyId));
    }
}
