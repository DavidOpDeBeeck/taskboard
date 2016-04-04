package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.api.application.Application;
import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

        HttpEntity<String> httpEntity = new HttpEntity<>( "title=" + title + "&sequence=" + sequence + "&completed=" + completed, requestHeaders );
        HttpEntity<String> response = restTemplate.exchange( baseUrl() + "/projects/" + project.getId() + "/lanes", HttpMethod.POST, httpEntity, String.class );

        HttpHeaders headers = response.getHeaders();
        String location = headers.getLocation()
                .toString();

        HttpEntity<Lane> apiResponse = restTemplate.exchange( location, HttpMethod.GET, null, Lane.class );

        assertNotNull( apiResponse );

        Lane lane = laneDAO.getById( apiResponse.getBody()
                .getId() );

        assertEquals( lane.getId(), apiResponse.getBody()
                .getId() );
        assertEquals( lane.getTitle(), apiResponse.getBody()
                .getTitle() );
        assertEquals( lane.getSequence(), apiResponse.getBody()
                .getSequence() );
        assertEquals( lane.isCompleted(), apiResponse.getBody()
                .isCompleted() );
    }

    @Test
    public void testUpdateLane() throws Exception
    {
        String title = "To Verify";
        int sequence = 3;
        boolean completed = true;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

        HttpEntity<String> httpEntity = new HttpEntity<>( "title=" + title + "&sequence=" + sequence + "&completed=" + completed, requestHeaders );
        HttpEntity<String> response = restTemplate.exchange( url(), HttpMethod.POST, httpEntity, String.class );

        HttpHeaders headers = response.getHeaders();
        String location = headers.getLocation()
                .toString();

        HttpEntity<Lane> apiResponse = restTemplate.exchange( location, HttpMethod.GET, null, Lane.class );

        assertNotNull( apiResponse );

        Lane lane = laneDAO.getById( apiResponse.getBody()
                .getId() );

        assertEquals( lane.getId(), apiResponse.getBody()
                .getId() );
        assertEquals( lane.getTitle(), apiResponse.getBody()
                .getTitle() );
        assertEquals( lane.getSequence(), apiResponse.getBody()
                .getSequence() );
        assertEquals( lane.isCompleted(), apiResponse.getBody()
                .isCompleted() );

        title = "ToDo";
        sequence = 1;
        completed = false;

        httpEntity = new HttpEntity<>( "title=" + title + "&sequence=" + sequence + "&completed=" + completed, requestHeaders );
        restTemplate.put( url() + "/" + lane.getId(), httpEntity, Lane.class );

        lane = laneDAO.getById( apiResponse.getBody()
                .getId() );

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

    @Override

    protected String context()
    {
        return String.format( "projects/%s/lanes", project.getId() );
    }
}