package com.example;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class Dependency implements BeanPostProcessor {
    public Dependency() {
        System.out.println("1.1 Dependency constructor");
    }
}
