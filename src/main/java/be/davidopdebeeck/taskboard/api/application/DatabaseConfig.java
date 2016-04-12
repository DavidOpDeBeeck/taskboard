package be.davidopdebeeck.taskboard.api.application;

import be.davidopdebeeck.taskboard.dao.LaneDAO;
import be.davidopdebeeck.taskboard.dao.ProjectDAO;
import be.davidopdebeeck.taskboard.dao.TaskDAO;
import be.davidopdebeeck.taskboard.dao.impl.LaneDAOImpl;
import be.davidopdebeeck.taskboard.dao.impl.ProjectDAOImpl;
import be.davidopdebeeck.taskboard.dao.impl.TaskDAOImpl;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import be.davidopdebeeck.taskboard.service.TaskBoardImpl;
import org.springframework.beans.factory.annotation.Value;
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

    @Value( "${host}" )
    private String host;

    @Value( "${port}" )
    private int port;

    @Value( "${database}" )
    private String database;

    @Value( "${user}" )
    private String user;

    @Value( "${password}" )
    private String password;


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
    public LaneDAO laeDAO()
    {
        LaneDAOImpl laneDAO = new LaneDAOImpl();
        laneDAO.setJdbcTemplate( jdbcTemplate() );
        return laneDAO;
    }

    @Bean
    public TaskDAO taskDAO()
    {
        TaskDAOImpl taskDAO = new TaskDAOImpl();
        taskDAO.setJdbcTemplate( jdbcTemplate() );
        return taskDAO;
    }

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName( "com.mysql.jdbc.Driver" );
        dataSource.setUrl( String.format( "jdbc:mysql://%s:%d/%s", host, port, database ) );
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
