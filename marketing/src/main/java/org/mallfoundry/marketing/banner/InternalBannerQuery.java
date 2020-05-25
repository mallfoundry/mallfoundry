package org.mallfoundry.marketing.banner;

import org.mallfoundry.data.PageableSupport;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class InternalBannerQuery extends PageableSupport implements BannerQuery {

    private Set<BannerPage> pages;

    private Set<BannerDateType> dateTypes;

    private Date dateFrom;

    private Date dateTo;

    private Set<BannerLocation> locations;

    private Set<BannerVisibility> visibilities;
}
