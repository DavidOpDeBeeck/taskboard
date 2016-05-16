package be.davidopdebeeck.taskboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories( "be.davidopdebeeck.taskboard" )
public class ApplicationConfig
{

    @Value( "${datasource.url}" )
    private String url;

    @Value( "${datasource.user}" )
    private String user;

    @Value( "${datasource.password}" )
    private String password;

    @Bean
    public JpaTransactionManager transactionManager()
    {
        return new JpaTransactionManager( entityManagerFactory().getObject() );
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter()
    {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase( Database.MYSQL );
        return jpaVendorAdapter;
    }

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName( "com.mysql.jdbc.Driver" );
        dataSource.setUrl( url );
        dataSource.setUsername( user );
        dataSource.setPassword( password );

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter( jpaVendorAdapter() );
        factoryBean.setPackagesToScan( "be.davidopdebeeck.taskboard" );
        factoryBean.setDataSource( dataSource() );
        return factoryBean;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
