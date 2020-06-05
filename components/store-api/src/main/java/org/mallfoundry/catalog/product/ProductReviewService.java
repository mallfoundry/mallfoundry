package org.mallfoundry.catalog.product;

public interface ProductReviewService {

    ProductReview createReview();

    void addReview(ProductReview review) throws ProductReviewException;

    void approveReview(String reviewId) throws ProductReviewException;

    void disapproveReview(String reviewId) throws ProductReviewException;
}
