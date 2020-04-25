package com.mallfoundry.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

public final class ApplicationContextUtils {

    private ApplicationContext context;

    public ApplicationContext getApplicationContext() {
        return this.context;
    }

    @Configuration
    class ApplicationContextConfiguration implements ApplicationContextAware {
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            ApplicationContextUtils.this.context = applicationContext;
        }
    }
}
