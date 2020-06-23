package org.mallfoundry.browsing.repository.jpa;

import org.mallfoundry.browsing.BrowsingProduct;
import org.mallfoundry.browsing.BrowsingProductQuery;
import org.mallfoundry.browsing.BrowsingProductRepository;
import org.mallfoundry.browsing.InternalBrowsingProduct;
import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaBrowsingProductRepository implements BrowsingProductRepository {

    private final JpaBrowsingProductRepositoryDelegate browsingProductRepository;

    public JpaBrowsingProductRepository(JpaBrowsingProductRepositoryDelegate browsingProductRepository) {
        this.browsingProductRepository = browsingProductRepository;
    }

    @Override
    public BrowsingProduct save(BrowsingProduct browsingProduct) {
        return this.browsingProductRepository.save(InternalBrowsingProduct.of(browsingProduct));
    }

    @Override
    public Optional<BrowsingProduct> findByIdAndBrowserId(String id, String browserId) {
        return CastUtils.cast(this.browsingProductRepository.findByIdAndBrowserId(id, browserId));
    }

    @Override
    public List<BrowsingProduct> findAllByIdInAndBrowserId(Iterable<String> ids, String browserId) {
        return CastUtils.cast(this.browsingProductRepository.findAllByIdInAndBrowserId(ids, browserId));
    }

    @Override
    public SliceList<BrowsingProduct> findAll(BrowsingProductQuery query) {
        return CastUtils.cast(this.browsingProductRepository.findAll(query));
    }

    @Override
    public void delete(BrowsingProduct browsingProduct) {

    }

    @Override
    public void deleteAll(Iterable<? extends BrowsingProduct> browsingProducts) {

    }

    @Override
    public long count(BrowsingProductQuery query) {
        return this.browsingProductRepository.count(query);
    }
}