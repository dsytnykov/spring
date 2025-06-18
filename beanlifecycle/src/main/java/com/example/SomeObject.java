package com.example;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class SomeObject implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if(bean instanceof BeanlifecycleApplication) {
            System.out.println("5. BeanlifecycleApplication postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if(bean instanceof BeanlifecycleApplication) {
            System.out.println("8. BeanlifecycleApplication postProcessAfterInitialization");
        }
        return bean;
    }
}
