package be.davidopdebeeck.taskboard.api.config;

import be.davidopdebeeck.taskboard.service.TaskBoard;
import be.davidopdebeeck.taskboard.service.TaskBoardImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig
{

    @Value( "${datasource.url}" )
    private String url;

    @Value( "${datasource.user}" )
    private String user;

    @Value( "${datasource.password}" )
    private String password;

    @Bean
    public TaskBoard taskBoard()
    {
        return new TaskBoardImpl();
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
    public JdbcTemplate jdbcTemplate()
    {
        return new JdbcTemplate( dataSource() );
    }
}
