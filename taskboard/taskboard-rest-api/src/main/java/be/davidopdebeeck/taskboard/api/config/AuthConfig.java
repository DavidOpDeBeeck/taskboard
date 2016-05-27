package be.davidopdebeeck.taskboard.api.config;

import be.davidopdebeeck.taskboard.api.auth.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AuthConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors( InterceptorRegistry registry )
    {
        registry.addInterceptor( authInterceptor );
    }
}