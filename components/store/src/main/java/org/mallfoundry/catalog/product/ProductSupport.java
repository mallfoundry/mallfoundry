package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.catalog.DefaultOptionSelection;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.Positions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class ProductSupport implements Product {

    private String id;

    private String storeId;

    private ProductType type;

    private ProductStatus status;

    private String name;

    private String description;

    private Set<String> collections = new HashSet<>();

    private BigDecimal price;

    private BigDecimal marketPrice;

    private List<ProductOption> options = new ArrayList<>();

    private List<ProductAttribute> attributes = new ArrayList<>();

    private List<ProductVariant> variants = new ArrayList<>();

    private List<String> imageUrls = new ArrayList<>();

    private List<String> videoUrls = new ArrayList<>();

    private String shippingOrigin;

    private boolean freeShipping;

    private BigDecimal fixedShippingCost;

    private String shippingRateId;

    private Date createdTime;

    public ProductSupport() {

    }

    public ProductSupport(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return CollectionUtils.isEmpty(this.getVariants())
                ? this.price
                : this.getVariants().stream()
                        .map(ProductVariant::getPrice)
                        .max(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);
    }

    @Override
    public int getInventoryQuantity() {
        return CollectionUtils.isEmpty(this.getVariants())
                ? 0
                : this.getVariants()
                        .stream()
                        .mapToInt(ProductVariant::getInventoryQuantity)
                        .sum();
    }

    @Override
    public InventoryStatus getInventoryStatus() {
        return this.getInventoryQuantity() == 0
                ? InventoryStatus.OUT_OF_STOCK
                : InventoryStatus.IN_STOCK;
    }

    @Override
    public void addVariant(ProductVariant variant) {
        this.getVariants().add(variant);
    }

    @Override
    public void adjustVariantInventoryQuantity(String variantId, int quantityDelta) {
        this.getVariant(variantId).orElseThrow().adjustInventoryQuantity(quantityDelta);
    }

    @Override
    public Optional<ProductVariant> getVariant(String id) {
        return this.getVariants()
                .stream()
                .filter(variant -> Objects.equals(variant.getId(), id))
                .findFirst();
    }

    @Override
    public ProductVariant createVariant(String id) {
        var variant = new InternalProductVariant();
        variant.setId(id);
        variant.setProductId(this.getId());
        variant.setStoreId(this.getStoreId());
        return variant;
    }

    @Override
    public ProductOption createOption(String id) {
        return new InternalProductOption(id);
    }

    @Override
    public Optional<ProductOption> getOption(String name) {
        return this.options.stream().filter(option -> Objects.equals(option.getName(), name)).findFirst();
    }

    @Override
    public void addOption(ProductOption option) {
        this.options.add(option);
        Positions.sort(this.options);
    }

    @Override
    public Optional<OptionSelection> selectOption(final String name, final String label) {
        return this.getOption(name)
                .map(option -> Map.entry(option, option.getValue(label).orElseThrow()))
                .map(entry -> new DefaultOptionSelection(entry.getKey().getId(), name, entry.getValue().getId(), label));
    }

    @Override
    public ProductAttribute createAttribute(String name, String value) {
        return new InternalProductAttribute(name, value);
    }

    @Override
    public ProductAttribute createAttribute(String namespace, String name, String value) {
        return new InternalProductAttribute(namespace, name, value);
    }

    @Override
    public Optional<ProductAttribute> getAttribute(String namespace, String name) {
        return Optional.empty();
    }

    @Override
    public void addImageUrl(String imageUrl) {
        this.getImageUrls().add(imageUrl);
    }

    @Override
    public void addVideoUrl(String video) {
        this.getVideoUrls().add(video);
    }

    @Override
    public void addAttribute(ProductAttribute attribute) {
        this.getAttributes().add(attribute);
        Positions.sort(this.getAttributes());
    }

    @Override
    public void create() {
        this.setCreatedTime(new Date());
    }
}
