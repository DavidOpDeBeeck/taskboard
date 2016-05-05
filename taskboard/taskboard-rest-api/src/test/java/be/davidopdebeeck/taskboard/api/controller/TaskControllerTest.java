package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.api.Application;
import be.davidopdebeeck.taskboard.api.dto.TaskDTO;
import be.davidopdebeeck.taskboard.core.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration( classes = Application.class )
@WebIntegrationTest
public class TaskControllerTest extends ControllerTest
{

    @Test
    public void testUpdateTask()
    {
        String title1 = "Test Task #1";
        String title2 = "Test Task #2";
        String description = "Make a Task description";
        String assignee = "David";

        Task task = new Task( title1, description, assignee );

        taskDAO.create( task );

        TaskDTO dto = new TaskDTO();

        dto.setTitle( title2 );
        dto.setDescription( description );
        dto.setAssignee( assignee );

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType( MediaType.APPLICATION_JSON );

        HttpEntity<?> httpEntity = new HttpEntity<>( dto, requestHeaders );
        restTemplate.put( url() + "/" + task.getId(), httpEntity, Task.class );

        Task newTask = taskDAO.getById( task.getId() );
        assertEquals( task.getId(), newTask.getId() );
        assertEquals( title2, newTask.getTitle() );
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
        return "tasks";
    }
}