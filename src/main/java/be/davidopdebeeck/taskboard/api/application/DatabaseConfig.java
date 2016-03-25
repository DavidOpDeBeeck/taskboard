package be.davidopdebeeck.taskboard.api.application;

import be.davidopdebeeck.taskboard.dao.ProjectDAO;
import be.davidopdebeeck.taskboard.dao.impl.ProjectDAOImpl;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import be.davidopdebeeck.taskboard.service.TaskBoardImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan( "be.davidopdebeeck.taskboard" )
public class DatabaseConfig
{

    @Bean
    public TaskBoard taskBoard()
    {
        return new TaskBoardImpl();
    }

    @Bean
    public ProjectDAO projectDAO()
    {
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        projectDAO.setJdbcTemplate( jdbcTemplate() );
        return projectDAO;
    }

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName( "com.mysql.jdbc.Driver" );
        dataSource.setUrl( "jdbc:mysql://localhost:3306/taskboard" );
        dataSource.setUsername( "David" );
        dataSource.setPassword( "1234" );

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate()
    {
        return new JdbcTemplate( dataSource() );
    }
}