package org.mallfoundry.district;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRegion {

    @JsonProperty("part")
    private String name;

    private List<Province> provinces;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Province {
        private String name;
        @JsonProperty("regionId")
        private String code;
        private List<City> cities;

    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class City {
        private String name;
        @JsonProperty("regionId")
        private String code;
        private List<County> counties;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class County {
        private String name;
        @JsonProperty("regionId")
        private String code;
    }
}
