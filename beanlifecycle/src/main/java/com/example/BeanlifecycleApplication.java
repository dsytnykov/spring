package com.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/*It can be done in the following ways:
* Using XML (also called Declarative Approach)
* Using Spring Interfaces (also called Programmatic Approach)
* Using Annotations
* */
@SpringBootApplication
public class BeanlifecycleApplication implements
		InitializingBean,
		DisposableBean,
		ApplicationContextAware,
		BeanNameAware,
		BeanFactoryAware {

	private ApplicationContext context;
	private String beanName;

	@Autowired
	private Dependency dependency;

	public static void main(String[] args) {
		System.out.println("0. Main method");
		SpringApplication.run(BeanlifecycleApplication.class, args);
		System.out.println("9. Exit main method");
	}

	public BeanlifecycleApplication() {
		System.out.println("1. Constructor");
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
		System.out.println("2. setBeanName method");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("3. setBeanFactory method");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
		System.out.println("4. setApplicationContext method");
	}

	@PostConstruct
	public void init() {
		System.out.println("6. Init method. For example: loading some configurations, creating database connections, etc.");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("7. AfterPropertiesSet method:  Custom initialization logic");
	}

	public void destroy() {
		System.out.println("11. Destroy method because DisposableBean interface");
	}

	@PreDestroy
	public void anyName() {
		System.out.println("10. Destroy method: cleanup something such as closing database connections");
	}


}
