package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( "/tasks" )
public class TaskController
{

    @Autowired
    TaskBoard taskBoard;

    @RequestMapping( value = "/{taskId}", method = RequestMethod.DELETE )
    public ResponseEntity removeTask( @PathVariable( "taskId" ) String taskId
    )
    {
        taskBoard.removeTask( taskId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}
