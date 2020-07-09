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

package org.mallfoundry.browsing.repository.jpa;

import org.mallfoundry.browsing.BrowsingProduct;
import org.mallfoundry.browsing.BrowsingProductQuery;
import org.mallfoundry.browsing.BrowsingProductRepository;
import org.mallfoundry.browsing.InternalBrowsingProduct;
import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<BrowsingProduct> findAllByIdInAndBrowserId(Collection<String> ids, String browserId) {
        return CastUtils.cast(this.browsingProductRepository.findAllByIdInAndBrowserId(ids, browserId));
    }

    @Override
    public SliceList<BrowsingProduct> findAll(BrowsingProductQuery query) {
        return CastUtils.cast(this.browsingProductRepository.findAll(query));
    }

    @Override
    public void delete(BrowsingProduct browsingProduct) {
        this.browsingProductRepository.deleteById(
                new JpaBrowsingProductId(browsingProduct.getBrowserId(), browsingProduct.getId()));
    }

    @Override
    public void deleteAll(Collection<? extends BrowsingProduct> browsingProducts) {
        var ids = browsingProducts.stream().map(product -> new JpaBrowsingProductId(product.getBrowserId(), product.getId()))
                .collect(Collectors.toUnmodifiableList());
        this.browsingProductRepository.deleteAllByIdIn(ids);
    }

    @Override
    public long count(BrowsingProductQuery query) {
        return this.browsingProductRepository.count(query);
    }
}
