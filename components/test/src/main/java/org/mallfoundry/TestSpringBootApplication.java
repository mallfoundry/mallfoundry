package org.mallfoundry;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableTransactionManagement
@EnableElasticsearchRepositories(basePackageClasses = Version.class)
@EnableJpaRepositories(basePackageClasses = Version.class)
@SpringBootApplication(scanBasePackageClasses = Version.class)
public class TestSpringBootApplication {
}
