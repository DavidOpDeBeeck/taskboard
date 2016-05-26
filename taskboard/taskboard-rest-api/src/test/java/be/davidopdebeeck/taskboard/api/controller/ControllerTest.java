package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.repository.LaneRepository;
import be.davidopdebeeck.taskboard.repository.ProjectRepository;
import be.davidopdebeeck.taskboard.repository.TaskRepository;
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

    RestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected LaneRepository laneRepository;

    @Autowired
    protected TaskRepository taskRepository;

    String url()
    {
        return baseUrl() + "/" + context();
    }

    String baseUrl()
    {
        return ( ssl ? "https" : "http" ) + "://" + address + ":" + port;
    }

    protected abstract String context();

}
