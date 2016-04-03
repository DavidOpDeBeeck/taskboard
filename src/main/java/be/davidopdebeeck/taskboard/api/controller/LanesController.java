package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( "/lanes" )
public class LanesController
{

    @Autowired
    TaskBoard taskBoard;

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<Lane> getLane( @PathVariable( "id" ) String id )
    {
        Lane lane = taskBoard.getLaneById( id );
        return new ResponseEntity<>( lane, HttpStatus.OK );
    }

}
