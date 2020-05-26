package org.mallfoundry.catalog;

import java.util.Date;

public interface ProductReview {

    String getId();

    String getProductId();

    String getReviewerId();

    String getReviewer();

    void setReviewer(String reviewer);

    int getRating();

    void setRating(int rating);

    String getTitle();

    void setTitle(String title);

    String getComment();

    void setComment(String comment);

    ProductReviewStatus getStatus();

    Date getReviewedTime();

    void approve();

    void disapprove();

}
