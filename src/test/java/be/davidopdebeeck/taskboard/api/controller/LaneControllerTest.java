package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.api.application.Application;
import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration( classes = Application.class )
@WebIntegrationTest
public class LaneControllerTest extends ControllerTest
{

    private Project project;

    @Before
    public void setUp()
    {
        Project project = new Project( "Test Project" );
        projectDAO.create( project );
        this.project = project;
    }

    @After
    public void breakDown()
    {
        projectDAO.remove( project );
    }

    @Test
    public void testGetLane() throws Exception
    {
        String title = "To Verify";
        int sequence = 3;
        boolean completed = true;

        Lane lane = new Lane( title, sequence, completed );
        laneDAO.create( lane );

        HttpEntity<Lane> apiResponse = restTemplate.exchange( url() + "/" + lane.getId(), HttpMethod.GET, null, Lane.class );

        assertNotNull( apiResponse );

        assertEquals( lane.getId(), apiResponse.getBody().getId() );
        assertEquals( lane.getTitle(), apiResponse.getBody().getTitle() );
        assertEquals( lane.getSequence(), apiResponse.getBody().getSequence() );
        assertEquals( lane.isCompleted(), apiResponse.getBody().isCompleted() );
    }

    @Test
    public void testUpdateLane() throws Exception
    {
        String title = "To Verify";
        int sequence = 3;
        boolean completed = true;

        Lane lane = new Lane( title, sequence, completed );

        laneDAO.create( lane );
        projectDAO.addLane( project, lane );

        title = "ToDo";
        sequence = 1;
        completed = false;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

        HttpEntity<String> httpEntity = new HttpEntity<>( "title=" + title + "&sequence=" + sequence + "&completed=" + completed, requestHeaders );
        restTemplate.put( url() + "/" + lane.getId(), httpEntity, Lane.class );

        lane = laneDAO.getById( lane.getId() );

        assertEquals( title, lane.getTitle() );
        assertEquals( sequence, lane.getSequence() );
        assertEquals( completed, lane.isCompleted() );
    }

    @Test
    public void testRemoveLane()
    {
        Lane lane = new Lane( "Test Lane", 0, false );
        String laneId = lane.getId();

        laneDAO.create( lane );

        restTemplate.delete( url() + "/" + laneId );

        Lane removedLane = laneDAO.getById( laneId );
        assertNull( removedLane );
    }

    @Test
    public void testAddTaskToLane() throws Exception
    {
        Lane lane = new Lane( "To Verify", 3, true );

        laneDAO.create( lane );
        projectDAO.addLane( project, lane );

        String title = "Make a Task";
        String description = "Make a Task description";
        String assignee = "David";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

        HttpEntity<String> httpEntity = new HttpEntity<>( "title=" + title + "&description=" + description + "&assignee=" + assignee, requestHeaders );
        HttpEntity<String> response = restTemplate.exchange( url() + "/" + lane.getId() + "/tasks", HttpMethod.POST, httpEntity, String.class );

        HttpHeaders headers = response.getHeaders();
        String location = headers.getLocation().toString();

        HttpEntity<Task> apiResponse = restTemplate.exchange( location, HttpMethod.GET, null, Task.class );

        assertNotNull( apiResponse );

        Task task = taskDAO.getById( apiResponse.getBody().getId() );

        assertEquals( task.getId(), apiResponse.getBody().getId() );
        assertEquals( task.getTitle(), apiResponse.getBody().getTitle() );
        assertEquals( task.getDescription(), apiResponse.getBody().getDescription() );
        assertEquals( task.getAssignee(), apiResponse.getBody().getAssignee() );
    }

    @Override

    protected String context()
    {
        return "lanes";
    }
}