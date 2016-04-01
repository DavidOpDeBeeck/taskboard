package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.dao.ProjectDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.web.client.RestTemplate;


public abstract class ControllerTest
{

    @Value( "${server.address}" )
    private String address;

    @Value( "${server.port}" )
    private int port;

    @Value( "${server.ssl.enabled}" )
    private boolean ssl;

    protected RestTemplate restTemplate = new TestRestTemplate();

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    protected ProjectDAO projectDAO;

    protected String url()
    {
        return baseUrl() + "/" + context();
    }

    protected String baseUrl()
    {
        return ( ssl ? "https" : "http" ) + "://" + address + ":" + port;
    }

    protected abstract String context();

}
