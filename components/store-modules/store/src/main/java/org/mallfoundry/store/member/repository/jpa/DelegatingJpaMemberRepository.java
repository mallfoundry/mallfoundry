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

package org.mallfoundry.store.member.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.member.Member;
import org.mallfoundry.store.member.MemberId;
import org.mallfoundry.store.member.MemberQuery;
import org.mallfoundry.store.member.MemberRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaMemberRepository implements MemberRepository {

    private final JpaMemberRepository repository;

    public DelegatingJpaMemberRepository(JpaMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public Member create(MemberId id) {
        return new JpaMember(id);
    }

    @Override
    public Optional<Member> findById(MemberId id) {
        return CastUtils.cast(this.repository.findById(new JpaMemberId(id)));
    }

    @Override
    public SliceList<Member> findAll(MemberQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(query.getPage()).limit(query.getLimit())
                .totalSize(page.getTotalElements())
                .cast();
    }

    @Override
    public Member save(Member member) {
        return this.repository.save(JpaMember.of(member));
    }

    @Override
    public void delete(Member member) {
        this.repository.delete(JpaMember.of(member));
    }

    @Override
    public void deleteAllByStoreId(StoreId storeId) {
        this.repository.deleteAllByTenantIdAndStoreId(storeId.getTenantId(), storeId.getId());
    }
}
