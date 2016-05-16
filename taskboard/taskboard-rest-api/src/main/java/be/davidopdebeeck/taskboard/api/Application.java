package be.davidopdebeeck.taskboard.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan( { "be.davidopdebeeck.taskboard.core" } )
@ComponentScan( basePackages = { "be.davidopdebeeck.taskboard" } )
@EnableJpaRepositories( basePackages = { "be.davidopdebeeck.taskboard" } )
@SpringBootApplication( scanBasePackages = "be.davidopdebeeck.taskboard.api" )
public class Application
{

    public static void main( String[] args )
    {
        SpringApplication.run( Application.class, args );
    }

}
