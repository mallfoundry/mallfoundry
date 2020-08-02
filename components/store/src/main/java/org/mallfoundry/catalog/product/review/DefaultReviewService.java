/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.catalog.product.review;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.discuss.Author;
import org.mallfoundry.discuss.AuthorType;
import org.mallfoundry.discuss.DefaultAuthor;
import org.mallfoundry.security.SubjectHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DefaultReviewService implements ReviewService {

    private final ReviewProcessorsInvoker processorsInvoker;

    private final ReviewRepository reviewRepository;

    private final ReviewReplyRepository commentRepository;

    public DefaultReviewService(ReviewProcessorsInvoker processorsInvoker,
                                ReviewRepository reviewRepository,
                                ReviewReplyRepository commentRepository) {
        this.processorsInvoker = processorsInvoker;
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ReviewReplyQuery createReviewReplyQuery() {
        return null;
    }

    @Override
    public ReviewQuery createReviewQuery() {
        return new DefaultReviewQuery();
    }

    @Override
    public Review createReview(String reviewId) {
        return this.reviewRepository.create(reviewId);
    }

    @Transactional
    @Override
    public void review(List<Review> reviews) throws ReviewException {
        reviews.forEach(Review::review);
        reviews = this.processorsInvoker.invokePreProcessAddProductReviews(reviews);
        this.reviewRepository.saveAll(reviews);
    }

    @Override
    public Optional<Review> getReview(String reviewId) {
        return this.reviewRepository.findById(reviewId);
    }

    @Override
    public SliceList<Review> getReviews(ReviewQuery query) {
        return this.reviewRepository.findAll(query);
    }

    private Review requiredReview(String reviewId) {
        return this.getReview(reviewId).orElseThrow();
    }

    private Author getAuthorFromComment(ReviewReply comment) {
        var author = Objects.requireNonNullElseGet(comment.getAuthor(), DefaultAuthor::new);
        var subject = SubjectHolder.getSubject();
        author.setAvatar(subject.getAvatar());
        author.setId(subject.getId());
        author.setNickname(subject.getNickname());
        author.setType(AuthorType.REPLIER);
        return author;
    }

    private void setCommentAuthor(Review review, ReviewReply comment) {
        var author = this.getAuthorFromComment(comment);
        var reviewer = review.getAuthor();
        if (Objects.equals(reviewer.getId(), author.getId())) {
            author.setType(reviewer.getType());
        }
        comment.setAuthor(author);
    }

    @Transactional
    @Override
    public ReviewReply replyReview(String reviewId, ReviewReply reply) throws ReviewException {
        var review = this.requiredReview(reviewId);
        this.setCommentAuthor(review, reply);
        review.reply(reply);
        return this.commentRepository.save(reply);
    }
}
