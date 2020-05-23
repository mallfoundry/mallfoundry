package com.mallfoundry.marketing.banner;

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
@Table(name = "marketing_banner")
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
