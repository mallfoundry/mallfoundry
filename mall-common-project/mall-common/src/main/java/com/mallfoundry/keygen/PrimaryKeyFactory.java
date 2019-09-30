package com.mallfoundry.keygen;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class PrimaryKeyFactory implements ApplicationContextAware {

    private ApplicationContext context;

    public PrimaryKeyGenerator<Long> sequence() {
        return context.getBean(AbstractSequencePrimaryKeyGenerator.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
