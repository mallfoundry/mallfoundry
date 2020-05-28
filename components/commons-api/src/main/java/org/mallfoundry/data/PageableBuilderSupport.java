package org.mallfoundry.data;

public abstract class PageableBuilderSupport<O extends Pageable, B extends PageableBuilder<O, B>>
        implements PageableBuilder<O, B> {

    private final O pageable;

    public PageableBuilderSupport(O pageable) {
        this.pageable = pageable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public B page(Integer page) {
        this.pageable.setPage(page);
        return (B) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public B limit(Integer limit) {
        this.pageable.setLimit(limit);
        return (B) this;
    }
}
