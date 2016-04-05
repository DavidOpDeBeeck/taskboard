package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin
@RestController
@RequestMapping( "/lanes/{laneId}" )
public class LaneController
{

    @Autowired
    TaskBoard taskBoard;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Lane> getLane( @PathVariable( "laneId" ) String laneId
    )
    {
        Lane lane = taskBoard.getLaneById( laneId );
        return new ResponseEntity<>( lane, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity removeTask( @PathVariable( "laneId" ) String laneId
    )
    {
        taskBoard.removeLane( laneId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity updateLane( @PathVariable( "laneId" ) String laneId, @RequestParam( "title" ) String title, @RequestParam( "sequence" ) Integer sequence, @RequestParam( "completed" ) Boolean completed
    )
    {
        Lane lane = taskBoard.getLaneById( laneId );

        lane.setTitle( title );
        lane.setSequence( sequence );
        lane.setCompleted( completed );

        taskBoard.updateLane( lane );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/tasks", method = RequestMethod.POST )
    public ResponseEntity addTaskToLane( @PathVariable( "laneId" ) String laneId, @RequestParam( value = "id", required = false ) String id, @RequestParam( "title" ) String title, @RequestParam( "description" ) String description, @RequestParam( "assignee" ) String assignee, UriComponentsBuilder b
    )
    {
        Task task;

        if ( id == null )
            task = taskBoard.addTaskToLane( laneId, title, description, assignee );
        else
            task = taskBoard.addTaskToLane( laneId, id );

        UriComponents components = b.path( "tasks/{id}" ).buildAndExpand( task.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/tasks/{taskId}", method = RequestMethod.DELETE )
    public ResponseEntity removeTaskFromLane( @PathVariable( "laneId" ) String laneId, @PathVariable( "taskId" ) String taskId
    )
    {
        taskBoard.removeTaskFromLane( laneId, taskId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}
