package org.mallfoundry;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan(annotationClass = Mapper.class)
@EnableCaching
@EnableTransactionManagement
@EnableElasticsearchRepositories(basePackageClasses = Version.class)
@EnableJpaRepositories(basePackageClasses = Version.class)
@SpringBootApplication(scanBasePackageClasses = Version.class)
public abstract class TestSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootApplication.class, args);
    }
}
