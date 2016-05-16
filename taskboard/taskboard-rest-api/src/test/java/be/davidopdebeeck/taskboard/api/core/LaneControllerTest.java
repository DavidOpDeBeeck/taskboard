package be.davidopdebeeck.taskboard.api.core;

import be.davidopdebeeck.taskboard.api.Application;
import be.davidopdebeeck.taskboard.api.dto.LaneDTO;
import be.davidopdebeeck.taskboard.api.dto.TaskDTO;
import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration( classes = Application.class )
@WebIntegrationTest
public class LaneControllerTest extends ControllerTest
{

    @Test
    public void testGetLane() throws Exception
    {
        Project project = new Project( "Test Project" );
        projectRepository.save( project );

        String title = "To Verify";
        int sequence = 1;
        boolean completed = true;

        Lane lane = new Lane( title, sequence, completed );
        laneRepository.save( lane );

        HttpEntity<Lane> apiResponse = restTemplate.exchange( url() + "/" + lane.getId(), HttpMethod.GET, null, Lane.class );

        assertNotNull( apiResponse );

        assertEquals( lane.getId(), apiResponse.getBody().getId() );
        assertEquals( lane.getTitle(), apiResponse.getBody().getTitle() );
        assertEquals( lane.getSequence(), apiResponse.getBody().getSequence() );
        assertEquals( lane.isCompleted(), apiResponse.getBody().isCompleted() );

        projectRepository.delete(project);
    }

    @Test
    public void testUpdateLane() throws Exception
    {
        String title = "To Verify";
        int sequence = 3;
        boolean completed = true;

        Project project = new Project( "Test Project" );
        Lane lane = new Lane( title, sequence, completed );

        projectRepository.save( project );
        laneRepository.save( lane );

        project.addLane( lane );
        projectRepository.save( project );

        title = "ToDo";
        sequence = 1;
        completed = false;

        LaneDTO dto = new LaneDTO();
        dto.setTitle( title );
        dto.setSequence( sequence );
        dto.setCompleted( completed );

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_JSON );

        HttpEntity<?> httpEntity = new HttpEntity<>( dto, requestHeaders );
        restTemplate.put( url() + "/" + lane.getId(), httpEntity, Lane.class );

        lane = laneRepository.findOne( lane.getId() );

        assertEquals( title, lane.getTitle() );
        assertEquals( sequence, lane.getSequence() );
        assertEquals( completed, lane.isCompleted() );

        projectRepository.delete(project);
    }

    @Test
    public void testRemoveLane()
    {
        Project project = new Project( "Test Project" );
        Lane lane = new Lane( "Test Lane", 0, false );

        projectRepository.save( project );
        laneRepository.save( lane );

        String laneId = lane.getId();

        restTemplate.delete( url() + "/" + laneId );

        Lane removedLane = laneRepository.findOne( laneId );
        assertNull( removedLane );

        projectRepository.delete(project);
    }

    @Test
    public void testAddTaskToLane() throws Exception
    {
        Project project = new Project( "Test Project" );
        Lane lane = new Lane( "To Verify", 3, true );

        projectRepository.save( project );
        laneRepository.save( lane );

        project.addLane( lane );
        projectRepository.save( project );

        String title = "Make a Task";
        String description = "Make a Task description";
        String assignee = "David";

        TaskDTO dto = new TaskDTO();

        dto.setTitle( title );
        dto.setDescription( description );
        dto.setAssignee( assignee );

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_JSON );

        HttpEntity<?> httpEntity = new HttpEntity<>( dto, requestHeaders );
        HttpEntity<String> response = restTemplate.exchange( url() + "/" + lane.getId() + "/tasks", HttpMethod.POST, httpEntity, String.class );

        HttpHeaders headers = response.getHeaders();
        String location = headers.getLocation().toString();

        HttpEntity<Task> apiResponse = restTemplate.exchange( location, HttpMethod.GET, null, Task.class );

        assertNotNull( apiResponse );

        Task task = taskRepository.findOne( apiResponse.getBody().getId() );

        assertEquals( task.getId(), apiResponse.getBody().getId() );
        assertEquals( task.getTitle(), apiResponse.getBody().getTitle() );
        assertEquals( task.getDescription(), apiResponse.getBody().getDescription() );
        assertEquals( task.getAssignee(), apiResponse.getBody().getAssignee() );

        projectRepository.delete(project);
    }

    @Override

    protected String context()
    {
        return "lanes";
    }
}