package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( "/tasks/{taskId}" )
public class TaskController
{

    @Autowired
    TaskBoard taskBoard;

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity updateTask( @PathVariable( "taskId" ) String taskId, @RequestParam( "title" ) String title, @RequestParam( "description" ) String description, @RequestParam( "assignee" ) String assignee
    )
    {
        Task task = taskBoard.getTaskById( taskId );

        task.setTitle( title );
        task.setDescription( description );
        task.setAssignee( assignee );

        taskBoard.updateTask( task );

        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Task> getTask( @PathVariable( "taskId" ) String taskId
    )
    {
        Task task = taskBoard.getTaskById( taskId );
        return new ResponseEntity<>( task, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity removeTask( @PathVariable( "taskId" ) String taskId
    )
    {
        taskBoard.removeTask( taskId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}
