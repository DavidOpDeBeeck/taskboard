package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping( "/projects" )
public class ProjectController
{

    @Autowired
    TaskBoard taskBoard;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Collection<Project>> getProjects()
    {
        return new ResponseEntity<>( taskBoard.getAllProjects(), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity addProjects( @RequestParam( "title" ) String title )
    {
        taskBoard.createProject( title );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<Project> getProjectById( @PathVariable( "id" ) String id )
    {
        return new ResponseEntity<>( taskBoard.getProjectById( id ), HttpStatus.OK );
    }

}
