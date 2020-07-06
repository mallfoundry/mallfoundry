package org.mallfoundry.catalog.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import static org.mallfoundry.i18n.MessageHolder.message;

public abstract class ProductMessages {

    private static final String FIELD_NOT_EMPTY_MESSAGE_CODE_KEY = "catalog.product.field.notEmpty";

    private static final String FIELD_EMPTY_MESSAGE_CODE_KEY = "catalog.product.field.empty";

    private static final String FIELD_GREATER_THAN_X_MESSAGE_CODE_KEY = "catalog.product.field.greaterThanX";

    private static final String VARIANT_NOT_FOUND_MESSAGE_CODE_KEY = "catalog.product.variant.notFound";

    private static final String DEFAULT_NOT_EMPTY_MESSAGE_FORMAT = "Product %s must not be empty";

    private static final String DEFAULT_EMPTY_MESSAGE_FORMAT = "Product %s must be empty";

    private static final String DEFAULT_GREATER_THAN_X_MESSAGE_FORMAT = "Product %s must be greater than %s";

    private static final String VARIANT_FIELD_NOT_EMPTY_MESSAGE_CODE_KEY = "catalog.product.variant.field.notEmpty";

    private static final String DEFAULT_VARIANT_NOT_EMPTY_MESSAGE_FORMAT = "Product variant %s must not be empty";

    private static String resolveFieldName(String name) {
        return message(String.format("catalog.product.fields.%s.label", name));
    }

    private static String resolveVariantFieldName(String name) {
        return message(String.format("catalog.product.variant.fields.%s.label", name));
    }

    public static Supplier<String> notEmpty(String name) {
        return () ->
                message(FIELD_NOT_EMPTY_MESSAGE_CODE_KEY, List.of(resolveFieldName(name)),
                        String.format(DEFAULT_NOT_EMPTY_MESSAGE_FORMAT, name));
    }

    public static String notEmptyMessage(String name) {
        return notEmpty(name).get();
    }

    public static Supplier<String> empty(String name) {
        return () ->
                message(FIELD_EMPTY_MESSAGE_CODE_KEY, List.of(resolveFieldName(name)),
                        String.format(DEFAULT_EMPTY_MESSAGE_FORMAT, name));
    }

    public static Supplier<String> greaterThanX(String name, BigDecimal number) {
        return () ->
                message(FIELD_GREATER_THAN_X_MESSAGE_CODE_KEY, List.of(resolveFieldName(name), number),
                        String.format(DEFAULT_GREATER_THAN_X_MESSAGE_FORMAT, name, number));
    }

    public static Supplier<String> greaterThanX(String name, long number) {
        return () ->
                message(FIELD_GREATER_THAN_X_MESSAGE_CODE_KEY, List.of(resolveFieldName(name), number),
                        String.format(DEFAULT_GREATER_THAN_X_MESSAGE_FORMAT, name, number));
    }

    public static Supplier<String> variantNotFound() {
        return () -> message(VARIANT_NOT_FOUND_MESSAGE_CODE_KEY, "Product variant does not exist");
    }

    public static Supplier<String> optionNotFound() {
        return () -> message(VARIANT_NOT_FOUND_MESSAGE_CODE_KEY, "Product variant does not exist");
    }

    public static Supplier<String> variantNotEmpty(String name) {
        return () ->
                message(VARIANT_FIELD_NOT_EMPTY_MESSAGE_CODE_KEY, List.of(resolveVariantFieldName(name)),
                        String.format(DEFAULT_VARIANT_NOT_EMPTY_MESSAGE_FORMAT, name));
    }
}
