package org.mallfoundry.catalog.product;

public class ProductReviewException extends RuntimeException {

    public ProductReviewException(String message) {
        super(message);
    }

    public ProductReviewException(String message, Throwable cause) {
        super(message, cause);
    }
}
