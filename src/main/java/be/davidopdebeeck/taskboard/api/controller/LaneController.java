package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( "/lanes" )
public class LaneController
{

    @Autowired
    TaskBoard taskBoard;

    @RequestMapping( value = "/{laneId}", method = RequestMethod.DELETE )
    public ResponseEntity removeTask( @PathVariable( "laneId" ) String laneId
    )
    {
        taskBoard.removeLane( laneId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}
