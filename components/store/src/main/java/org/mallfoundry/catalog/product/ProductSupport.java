package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.catalog.DefaultOptionSelection;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.Positions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
public abstract class ProductSupport implements MutableProduct {

    public ProductSupport(String id) {
        this.setId(id);
    }

    public BigDecimal getPrice() {
        return CollectionUtils.isEmpty(this.getVariants())
                ? this.getPrice()
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
    public ProductOption createOption(String id) {
        return new InternalProductOption(id);
    }

    @Override
    public Optional<ProductOption> getOption(String name) {
        return this.getOptions().stream().filter(option -> Objects.equals(option.getName(), name)).findFirst();
    }

    @Override
    public void addOption(ProductOption option) {
        this.getOptions().add(option);
        Positions.sort(this.getOptions());
    }

    @Override
    public Optional<OptionSelection> selectOption(final String name, final String label) {
        return this.getOption(name)
                .map(option -> Map.entry(option, option.getValue(label).orElseThrow()))
                .map(entry -> new DefaultOptionSelection(entry.getKey().getId(), name, entry.getValue().getId(), label));
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

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    abstract static class BuilderSupport implements Builder {

        protected final Product product;

        BuilderSupport(Product product) {
            this.product = product;
        }

        public Builder storeId(String storeId) {
            this.product.setStoreId(storeId);
            return this;
        }

        public Builder name(String name) {
            this.product.setName(name);
            return this;
        }

        public Builder type(ProductType type) {
            this.product.setType(type);
            return this;
        }

        public Builder status(ProductStatus status) {
            this.product.setStatus(status);
            return this;
        }

        @Override
        public Builder collections(Set<String> collections) {
            this.product.setCollections(collections);
            return this;
        }

        public Builder imageUrl(String image) {
            this.product.addImageUrl(image);
            return this;
        }

        public Builder videoUrl(String video) {
            this.product.addVideoUrl(video);
            return this;
        }

        @Override
        public Builder option(ProductOption option) {
            this.product.addOption(option);
            return this;
        }

        @Override
        public Builder option(Function<Product, ProductOption> option) {
            return this.option(option.apply(this.product));
        }

        @Override
        public Builder variant(ProductVariant variant) {
            this.product.addVariant(variant);
            return this;
        }

        @Override
        public Builder variant(Function<Product, ProductVariant> variant) {
            return this.variant(variant.apply(this.product));
        }

        @Override
        public Builder attribute(ProductAttribute attribute) {
            this.product.addAttribute(attribute);
            return this;
        }

        @Override
        public Builder attribute(Function<Product, ProductAttribute> attribute) {
            return this.attribute(attribute.apply(this.product));
        }

        @Override
        public Builder create() {
            this.product.create();
            return this;
        }

        public Product build() {
            return this.product;
        }
    }
}
