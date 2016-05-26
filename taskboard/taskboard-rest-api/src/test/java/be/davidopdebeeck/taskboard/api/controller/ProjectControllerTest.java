package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.api.Application;
import be.davidopdebeeck.taskboard.dto.LaneDTO;
import be.davidopdebeeck.taskboard.dto.ProjectUpdateDTO;
import be.davidopdebeeck.taskboard.core.Lane;
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
    public void testGetProjects()
    {
        Project p1 = new Project( "Test Project #1" );
        Project p2 = new Project( "Test Project #2" );
        Project p3 = new Project( "Test Project #3" );
        Project p4 = new Project( "Test Project #4" );

        projectRepository.save( p1 );
        projectRepository.save( p2 );
        projectRepository.save( p3 );
        projectRepository.save( p4 );

        ResponseEntity<List<Project>> apiResponse = restTemplate.exchange( url(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Project>>()
        {} );

        assertNotNull( apiResponse );

        List<Project> projects = apiResponse.getBody();

        assertNotNull( projects );

        assertTrue( projects.contains( p1 ) );
        assertTrue( projects.contains( p2 ) );
        assertTrue( projects.contains( p3 ) );
        assertTrue( projects.contains( p4 ) );

        projectRepository.delete( p1 );
        projectRepository.delete( p2 );
        projectRepository.delete( p3 );
        projectRepository.delete( p4 );
    }

    @Test
    public void testAddProject()
    {
        String title = "Test Project";

        ProjectUpdateDTO dto = new ProjectUpdateDTO();
        dto.setTitle( title );

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_JSON );

        HttpEntity<?> httpEntity = new HttpEntity<>( dto, requestHeaders );
        HttpEntity<String> response = restTemplate.exchange( url(), HttpMethod.POST, httpEntity, String.class );

        HttpHeaders headers = response.getHeaders();
        String location = headers.getLocation().toString();

        HttpEntity<Project> apiResponse = restTemplate.exchange( location, HttpMethod.GET, null, Project.class );

        assertNotNull( apiResponse );

        Project project = projectRepository.findOne( apiResponse.getBody().getId() );
        assertEquals( project.getId(), apiResponse.getBody().getId() );
        assertEquals( project.getTitle(), apiResponse.getBody().getTitle() );

        projectRepository.delete(project);
    }

    @Test
    public void testGetProject()
    {
        Project project = new Project( "Test Project" );

        projectRepository.save( project );
        String projectId = project.getId();

        Project apiResponse = restTemplate.getForObject( url() + "/" + projectId, Project.class );

        assertNotNull( apiResponse );
        assertEquals( project.getId(), apiResponse.getId() );
        assertEquals( project.getTitle(), apiResponse.getTitle() );

        projectRepository.delete(project);
    }

    @Test
    public void testUpdateProject()
    {
        String title1 = "Test Project #1";
        String title2 = "Test Project #2";

        Project project = new Project( title1 );

        projectRepository.save( project );

        ProjectUpdateDTO dto = new ProjectUpdateDTO();
        dto.setTitle( title2 );

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_JSON );

        HttpEntity<?> httpEntity = new HttpEntity<>( dto, requestHeaders );
        restTemplate.put( url() + "/" + project.getId(), httpEntity, Project.class );

        Project newProject = projectRepository.findOne( project.getId() );
        assertEquals( project.getId(), newProject.getId() );
        assertEquals( title2, newProject.getTitle() );

        projectRepository.delete(project);
    }

    @Test
    public void testRemoveProject()
    {
        Project project = new Project( "Test Project" );

        projectRepository.save( project );
        String projectId = project.getId();

        restTemplate.delete( url() + "/" + projectId );

        Project removedProject = projectRepository.findOne( projectId );
        assertNull( removedProject );

        projectRepository.delete(project);
    }

    @Test
    public void testAddLaneToProject()
    {
        String title = "To Verify";
        int sequence = 3;
        boolean completed = true;

        Project project = new Project( "Test Project" );

        projectRepository.save( project );

        LaneDTO dto = new LaneDTO();
        dto.setTitle( title );
        dto.setSequence( sequence );
        dto.setCompleted( completed );

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_JSON );

        HttpEntity<?> httpEntity = new HttpEntity<>( dto, requestHeaders );
        HttpEntity<String> response = restTemplate.exchange( url() + "/" + project.getId() + "/lanes", HttpMethod.POST, httpEntity, String.class );

        HttpHeaders headers = response.getHeaders();
        String location = headers.getLocation().toString();

        HttpEntity<Lane> apiResponse = restTemplate.exchange( location, HttpMethod.GET, null, Lane.class );

        assertNotNull( apiResponse );

        Lane lane = laneRepository.findOne( apiResponse.getBody().getId() );

        assertEquals( lane.getId(), apiResponse.getBody().getId() );
        assertEquals( lane.getTitle(), apiResponse.getBody().getTitle() );
        assertEquals( lane.getSequence(), apiResponse.getBody().getSequence() );
        assertEquals( lane.isCompleted(), apiResponse.getBody().isCompleted() );

        projectRepository.delete(project);
    }

    @Override
    protected String context()
    {
        return "projects";
    }
}