package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public ResponseEntity<Project> addProjects( @RequestParam( "title" ) String title )
    {
        Project project = taskBoard.createProject( title );
        return new ResponseEntity<>( project, HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<Project> getProjectById( @PathVariable( "id" ) String id )
    {
        return new ResponseEntity<>( taskBoard.getProjectById( id ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public ResponseEntity removeProjectById( @PathVariable( "id" ) String id )
    {
        taskBoard.removeProject( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/lanes", method = RequestMethod.POST )
    public ResponseEntity<Project> addLaneToProjectById( @PathVariable( "id" ) String id, @RequestParam( "title" ) String title, @RequestParam( "sequence" ) Integer sequence, @RequestParam( "completed" ) Boolean completed )
    {
        Project project = taskBoard.getProjectById( id );
        taskBoard.addLaneToProject( project.getId(), title, sequence, completed );
        return new ResponseEntity<>( taskBoard.getProjectById( id ), HttpStatus.OK );
    }

}
