package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.dao.LaneDAO;
import be.davidopdebeeck.taskboard.dao.ProjectDAO;
import be.davidopdebeeck.taskboard.dao.TaskDAO;
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

    @Autowired
    protected ProjectDAO projectDAO;

    @Autowired
    protected LaneDAO laneDAO;

    @Autowired
    protected TaskDAO taskDAO;

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
