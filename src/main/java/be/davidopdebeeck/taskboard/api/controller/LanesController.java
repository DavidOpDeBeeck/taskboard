package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( "/lanes/{id}" )
public class LanesController
{

    @Autowired
    TaskBoard taskBoard;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Lane> getLane( @PathVariable( "id" ) String id )
    {
        Lane lane = taskBoard.getLaneById( id );
        return new ResponseEntity<>( lane, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity updateLane( @PathVariable( "id" ) String id, @RequestParam( "title" ) String title, @RequestParam( "sequence" ) Integer sequence, @RequestParam( "completed" ) Boolean completed )
    {
        Lane lane = taskBoard.getLaneById( id );

        lane.setTitle( title );
        lane.setSequence( sequence );
        lane.setCompleted( completed );

        taskBoard.updateLane( lane );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}
