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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.browsing.BrowsingProduct;
import org.mallfoundry.browsing.BrowsingProductSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_browsing_product")
@IdClass(JpaBrowsingProductId.class)
public class JpaBrowsingProduct extends BrowsingProductSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Id
    @Column(name = "browser_id_")
    private String browserId;

    @Column(name = "name_")
    private String name;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "hits_")
    private int hits;

    @Column(name = "browsing_time_")
    private Date browsingTime;

    public JpaBrowsingProduct(String browserId, String productId) {
        this.browserId = browserId;
        this.id = productId;
        this.browsingTime = new Date();
    }

    public static JpaBrowsingProduct of(BrowsingProduct browsingProduct) {
        if (browsingProduct instanceof JpaBrowsingProduct) {
            return (JpaBrowsingProduct) browsingProduct;
        }
        var target = new JpaBrowsingProduct();
        BeanUtils.copyProperties(browsingProduct, target);
        return target;
    }
}
