package com.mallfoundry.district;

import com.mallfoundry.util.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetDistrictTests {

    @Test
    public void testGetProvinces() throws IOException {
        var document = Jsoup.parse(new File("D:\\202003301019.html"), "utf-8");
        var elements = document.select("td.xl7030721");
        var iterator = elements.iterator();
        while (iterator.hasNext()) {
            var codeElement = iterator.next();
            var codeText = codeElement.text();
            if (codeText.endsWith("0000")) {
                System.out.println(String.format("saveProvince(\"%s\", \"%s\");", codeText, iterator.next().text()));
            }
        }
    }

    @Test
    public void testGetCities() throws IOException {
        var document = Jsoup.parse(new File("D:\\202003301019.html"), "utf-8");
        var elements = document.select("td");
        var iterator = elements.iterator();
        List<Districts.District> cities = new ArrayList<>();
        while (iterator.hasNext()) {
            var codeElement = iterator.next();
            var codeText = codeElement.text();

            if (codeText.startsWith("11") && !codeText.endsWith("0000") ||
                    codeText.startsWith("12") && !codeText.endsWith("0000") ||
                    codeText.startsWith("31") && !codeText.endsWith("0000") ||
                    codeText.startsWith("50") && !codeText.endsWith("0000") ||
                    !codeText.endsWith("0000") && codeText.endsWith("00")
            ) {
                cities.add(Districts.cityOf(codeText, iterator.next().text()));
            }

        }
        System.out.println(cities.size());
        FileUtils.writeStringToFile(new File("d://cities.json"), JsonUtils.stringify(cities), Charset.defaultCharset());
    }

    @Test
    public void testGetCounties() throws IOException {
        var document = Jsoup.parse(new File("D:\\202003301019.html"), "utf-8");
        List<Element> elements = document.select("td.xl7130721")
                .stream().filter(Element::hasText)
                .collect(Collectors.toList());
        var iterator = elements.iterator();

        List<Districts.District> counties = new ArrayList<>();

        while (iterator.hasNext()) {
            var codeElement = iterator.next();
            var codeText = codeElement.text();
            var nameText = iterator.next().text();
            if (codeText.startsWith("11") ||
                    codeText.startsWith("12") ||
                    codeText.startsWith("31") ||
                    codeText.startsWith("50")) {
                continue;
            }

            Assert.isTrue(codeText.matches("\\d{6}"), codeText);
            counties.add(Districts.countyOf(codeText.trim(), nameText.trim()));
        }

        System.out.println(counties.size());
        FileUtils.writeStringToFile(new File("d://counties.json"), JsonUtils.stringify(counties), Charset.defaultCharset());
    }
}
