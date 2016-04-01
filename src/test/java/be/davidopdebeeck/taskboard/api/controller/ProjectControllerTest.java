package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.api.application.Application;
import be.davidopdebeeck.taskboard.core.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration( classes = Application.class )
@WebIntegrationTest
public class ProjectControllerTest extends ControllerTest
{


    @Test
    public void testGetProjects() throws Exception
    {
        Project p1 = new Project( "Test Project #1" );
        Project p2 = new Project( "Test Project #2" );
        Project p3 = new Project( "Test Project #3" );
        Project p4 = new Project( "Test Project #4" );

        projectDAO.create( p1 );
        projectDAO.create( p2 );
        projectDAO.create( p3 );
        projectDAO.create( p4 );

        ResponseEntity<List<Project>> apiResponse = restTemplate.exchange( url(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Project>>()
        {} );

        assertNotNull( apiResponse );

        List<Project> projects = apiResponse.getBody();

        assertNotNull( projects );

        assertTrue( projects.contains( p1 ) );
        assertTrue( projects.contains( p2 ) );
        assertTrue( projects.contains( p3 ) );
        assertTrue( projects.contains( p4 ) );

        projectDAO.remove( p1 );
        projectDAO.remove( p2 );
        projectDAO.remove( p3 );
        projectDAO.remove( p4 );
    }

    @Test
    public void testAddProjects() throws Exception
    {
        String title = "Test Project";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

        HttpEntity<String> httpEntity = new HttpEntity<>( "title=" + title, requestHeaders );
        Project apiResponse = restTemplate.postForObject( url(), httpEntity, Project.class );

        assertNotNull( apiResponse );

        Project project = projectDAO.getById( apiResponse.getId() );
        assertEquals( apiResponse.getId(), project.getId() );
        assertEquals( apiResponse.getTitle(), project.getTitle() );

        projectDAO.remove( project );
    }

    @Test
    public void testGetProjectById() throws Exception
    {
        Project project = new Project( "Test Project" );
        String projectId = project.getId();

        projectDAO.create( project );

        Project apiResponse = restTemplate.getForObject( url() + "/" + projectId, Project.class );

        assertNotNull( apiResponse );
        assertEquals( project.getId(), apiResponse.getId() );
        assertEquals( project.getTitle(), apiResponse.getTitle() );

        projectDAO.remove( project );
    }

    @Override
    protected String context()
    {
        return "projects";
    }
}