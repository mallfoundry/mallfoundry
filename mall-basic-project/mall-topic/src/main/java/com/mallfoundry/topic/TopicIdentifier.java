package com.mallfoundry.topic;

import java.util.UUID;

/**
 * Topic object identifier.
 */
public class TopicIdentifier {

    /**
     * Generate a new comment id.
     *
     * @return comment id
     */
    public static String newCommentId() {
        return uuid();
    }

    /**
     * Generate a new reply id.
     *
     * @return comment id
     */
    public static String newReplyId() {
        return uuid();
    }

    private static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
