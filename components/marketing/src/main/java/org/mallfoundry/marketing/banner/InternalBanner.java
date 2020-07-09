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

package org.mallfoundry.marketing.banner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_marketing_banner")
public class InternalBanner implements Banner {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Column(name = "content_")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "page_")
    private BannerPage page;

    @Enumerated(EnumType.STRING)
    @Column(name = "date_type_")
    private BannerDateType dateType;

    @Column(name = "date_from_")
    private Date dateFrom;

    @Column(name = "date_to_")
    private Date dateTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_")
    private BannerLocation location;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility_")
    private BannerVisibility visibility;

    public InternalBanner(String id) {
        this.id = id;
    }

    public static InternalBanner of(Banner banner) {
        if (banner instanceof InternalBanner) {
            return (InternalBanner) banner;
        }
        var target = new InternalBanner();
        BeanUtils.copyProperties(banner, target);
        return target;
    }

    @Override
    public void show() {
        this.setVisibility(BannerVisibility.VISIBLE);
    }

    @Override
    public void hide() {
        this.setVisibility(BannerVisibility.HIDDEN);
    }
}
