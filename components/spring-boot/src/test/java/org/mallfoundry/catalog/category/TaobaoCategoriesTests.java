/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.catalog.category;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mallfoundry.catalog.CategoryIcon;
import org.mallfoundry.catalog.ChildCategory;
import org.mallfoundry.util.JsonUtils;
import org.mallfoundry.util.Positions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TaobaoCategoriesTests {

    public String getIndustry(String industry) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie", " t=ccdfa3e071cba21957951bae289fac09; cna=xCDCFUnPkTMCASdEAFmQUOl8; l=cBIyB7RgqsYt3WWGBOCNIuI8aZSFSIRYouPRwNXXi_5dw6T65y7Ok-OkIF9627BdOrYB4LnofdJ9-etWwkjpIGhzFLXl.; thw=cn; WAPFDFDTGFG=%2B4cMKKP%2B8PI%2BuWC%2FRNZNmOd6Fjde; _w_app_lg=19; tracknick=%5Cu5510%5Cu679Ctgio; _cc_=VFC%2FuZ9ajQ%3D%3D; _m_h5_tk=0cd7aa56ae6355739665d147d3e40bde_1577000027697; _m_h5_tk_enc=1737232783076b2af955bfc716057b5b; cookie2=1d34784116b86e6b4d3ec3825e6e7803; _tb_token_=3f5eaee347883; isg=BLi4132ZEeXZLX3ON5zmm6EpiWSKiRzE3rscXfIpAPOmDVz3mjHyO548w0MYRtSD");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();

        String dataParam = "{\"appId\":\"3113\",\"params\":\"{\\\"catmap_version\\\":\\\"3.0\\\",\\\"industry\\\":\\\"" + industry + "\\\"}\"}";
        long t = System.currentTimeMillis();

        String sign = DigestUtils.md5Hex("0cd7aa56ae6355739665d147d3e40bde&" + t + "&12574478&" + dataParam);
        URI url = UriComponentsBuilder
                .fromHttpUrl("https://h5api.m.taobao.com/h5/mtop.relationrecommend.wirelessrecommend.recommend/2.0/")
                .query("jsv=2.4.5&appKey=12574478&&api=mtop.relationrecommend.WirelessRecommend.recommend&v=2.0&preventFallback=true&type=jsonp&dataType=jsonp&callback=mtopjsonp2")
                .queryParam("t", t)
                .queryParam("sign", sign)
                .queryParam("data", dataParam)
                .build()
//                .encode()
                .toUri();


        ResponseEntity<String> content = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String body = content.getBody().trim();
        return body.substring("mtopjsonp2(".length(), body.length() - 1);
    }


    @Test
    public void testGetIndustry() {
        String body = this.getIndustry("");
        CategoriesDataRoot root = JsonUtils.parse(body, CategoriesDataRoot.class);
        System.out.println(root);
    }


    @Test
    public void testGetIndustriesToFiles() throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource resource = resolver.getResource("classpath:/industry-list.json");
        InputStream inputStream = resource.getInputStream();
        String listStr = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        List<Industry> industries = JsonUtils.parse(listStr, List.class, Industry.class);

        for (Industry industry : industries) {
            String body = this.getIndustry(industry.id);
            CategoriesDataRoot root = JsonUtils.parse(body, CategoriesDataRoot.class);
            FileUtils.writeStringToFile(new File("D:\\taobao-categories\\" + industry.id + ".json"), JsonUtils.stringify(root), Charset.defaultCharset());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoriesDataModuleItem {
        public String pic;
        public String id;
        public String name;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoriesDataModule {

        public String title;

        public List<CategoriesDataModuleItem> items;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoriesDataResult {

        public List<CategoriesDataModule> moduleList;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoriesData {
        public List<CategoriesDataResult> result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoriesDataRoot {

        public CategoriesData data;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Industry {
        public String id;
        public String name;
    }


    @Test
    public void testIndustriesToCategories() throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource("classpath:/industry-list.json");

        List<ChildCategory> categories = new ArrayList<>();
        try (InputStream inputStream = resource.getInputStream()) {
            String listStr = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            List<Industry> industries = JsonUtils.parse(listStr, List.class, Industry.class);
            for (Industry industry : industries) {
                ChildCategory topCategory = new ChildCategory(industry.name);

                File file = new File("D:\\taobao-categories\\" + industry.id + ".json");
                String body = FileUtils.readFileToString(file, Charset.defaultCharset());
                CategoriesDataRoot root = JsonUtils.parse(body, CategoriesDataRoot.class);

                if (root.data.result.isEmpty()) {
                    break;
                }
                categories.add(topCategory);
                var moduleList = root.data.result.get(0).moduleList;
                moduleList.forEach(module -> {
                    var childCategory = new ChildCategory(module.title);
                    topCategory.addChildCategory(childCategory);
                    module.items.forEach(item -> {
                        var itemCategory = new ChildCategory(item.name);
                        try {
                            itemCategory.setImageUrl(item.pic);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        childCategory.addChildCategory(itemCategory);
                    });

                    Positions.sort(childCategory.getChildren());
                });

            }
        }

        Positions.sort(categories);

        String categoriesString = JsonUtils.stringify(categories);
        File categoriesFile = new File("D:\\mall-categories\\categories.json");
        FileUtils.writeStringToFile(categoriesFile, categoriesString, Charset.defaultCharset());
        System.out.println(categories);
    }


    private String getPic(String picUrl) {
        picUrl = "http:" + picUrl;
        String filename = FilenameUtils.getName(picUrl);
        File localFile = new File("D:\\mall\\data\\storage\\categories\\" + filename);

        try {
            if (!localFile.exists()) {
                FileUtils.writeByteArrayToFile(localFile, IOUtils.toByteArray(new URL(picUrl)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "http://102.mallfoundry.com:8077/static/categories/" + filename;
    }
}
