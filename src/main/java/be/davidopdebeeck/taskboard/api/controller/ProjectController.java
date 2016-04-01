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
        taskBoard.addLaneToProject( project.getId(), "ToDo", 0, false );
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

}
