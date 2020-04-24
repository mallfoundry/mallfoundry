package com.mallfoundry.district;

import com.mallfoundry.util.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DistrictTests {

    @Autowired
    private DistrictService districtService;

    @Rollback(false)
    @Transactional
    @Test
    public void testSaveProvinces() throws IOException {
        var jsonString = FileUtils.readFileToString(new File("d://region.json"), "utf-8");
        List<Region> regions = JsonUtils.parse(jsonString, List.class, Region.class);
        for (var region : regions) {
            for (var province : region.getProvinces()) {
                var savedProvince = this.saveProvince(province.getCode(), province.getName());
                for (var city : province.getCities()) {
                    var savedCity = this.saveCity(city.getCode(), city.getName(), savedProvince.getId());
                    for (var county : city.getCounties()) {
                        this.saveCounty(county.getCode(), county.getName(), savedCity.getId());
                    }
                }
            }

        }

    }

    private Province saveProvince(String code, String name) {
        return this.districtService.saveProvince(this.districtService.createProvince(code, name, "1"));
    }

    private void saveCounty(String code, String name, String cityId) {
        this.districtService.saveCounty(this.districtService.createCounty(code, name, cityId));
    }

    private City saveCity(String code, String name, String provinceId) {
        return this.districtService.saveCity(this.districtService.createCity(code, name, provinceId));
    }

}
