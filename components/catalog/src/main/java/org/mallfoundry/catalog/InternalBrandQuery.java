package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.PageableSupport;

import java.util.Set;

@Getter
@Setter
public class InternalBrandQuery extends PageableSupport implements BrandQuery {
    private Set<String> categories;
}
