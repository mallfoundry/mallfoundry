package com.github.shop.keygen;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

public abstract class PrimaryKeyHolder {

    private static ApplicationContext context;

    public static PrimaryKeyGenerator<Long> sequence() {
        return factoryProxy().sequence();
    }

    private static PrimaryKeyFactory factoryProxy() {
        return context.getBean(PrimaryKeyFactory.class);
    }

    @Configuration
    static class ContextHolder implements ApplicationContextAware {
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            PrimaryKeyHolder.context = applicationContext;
        }
    }
}
