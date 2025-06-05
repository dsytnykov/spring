package com.dsytnykov.mysql.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMySql",
        basePackages = "com.dsytnykov.mysql.repository",
        transactionManagerRef = "transactionManagerMySql")
public class DataSourceMySqlConfig {
    @Autowired
    private Environment env;

    @Bean(name = "dataSourceMySql")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.mysql.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("spring.mysql.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.mysql.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.mysql.datasource.password"));
        return dataSource;
    }

    @Bean(name = "entityManagerFactoryMySql")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.dsytnykov.mysql.model");

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(adapter);

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.dialect", env.getProperty("spring.mysql.hibernate.dialect"));
        props.put("hibernate.show_sql", env.getProperty("spring.hibernate.show-sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("spring.hibernate.hbm2ddl-auto"));
        entityManagerFactoryBean.setJpaPropertyMap(props);

        return entityManagerFactoryBean;
    }

    @Bean(name = "transactionManagerMySql")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
