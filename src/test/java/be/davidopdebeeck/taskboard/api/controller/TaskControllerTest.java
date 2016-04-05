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
public class TaskControllerTest extends ControllerTest
{

    private Project project;
    private Lane lane;

    @Before
    public void setUp()
    {
        Project project = new Project( "Test Project" );
        Lane lane = new Lane( "Test Lane", 0, false );

        projectDAO.create( project );
        laneDAO.create( lane );

        projectDAO.addLane( project, lane );

        this.project = project;
        this.lane = lane;
    }

    @After
    public void breakDown()
    {
        projectDAO.remove( project );
    }

    @Test
    public void testAddTaskToLane() throws Exception
    {
        String title = "Make a Task";
        String description = "Make a Task description";
        String assignee = "David";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

        HttpEntity<String> httpEntity = new HttpEntity<>( "title=" + title + "&description=" + description + "&assignee=" + assignee, requestHeaders );
        HttpEntity<String> response = restTemplate.exchange( url(), HttpMethod.POST, httpEntity, String.class );

        HttpHeaders headers = response.getHeaders();
        String location = headers.getLocation().toString();

        HttpEntity<Task> apiResponse = restTemplate.exchange( location, HttpMethod.GET, null, Task.class );

        assertNotNull( apiResponse );

        Task task = taskDAO.getById( apiResponse.getBody().getId() );

        assertEquals( task.getId(), apiResponse.getBody().getId() );
        assertEquals( task.getTitle(), apiResponse.getBody().getTitle() );
        assertEquals( task.getDescription(), apiResponse.getBody().getDescription() );
        assertEquals( task.getAssignee(), apiResponse.getBody().getAssignee() );

        projectDAO.remove( project );
    }

    @Test
    public void testUpdateTask()
    {
        String title1 = "Test Task #1";
        String title2 = "Test Task #2";
        String description = "Make a Task description";
        String assignee = "David";

        Task task = new Task( title1, description, assignee );

        taskDAO.create( task );

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

        HttpEntity<String> httpEntity = new HttpEntity<>( "title=" + title2 + "&description=" + description + "&assignee=" + assignee, requestHeaders );
        restTemplate.put( url() + "/" + task.getId(), httpEntity, Task.class );

        Task newTask = taskDAO.getById( task.getId() );
        assertEquals( task.getId(), newTask.getId() );
        assertEquals( title2, newTask.getTitle() );

        projectDAO.remove( project );
    }

    @Test
    public void testRemoveTaskFromLane()
    {
        Task task = new Task( "Test Task", "Test Task description", "David" );
        String taskId = task.getId();

        taskDAO.create( task );

        restTemplate.delete( url() + "/" + taskId );

        Task removedTask = taskDAO.getById( taskId );
        assertNull( removedTask );
    }

    @Override
    protected String context()
    {
        return String.format( "projects/%s/lanes/%s/tasks", project.getId(), lane.getId() );
    }
}