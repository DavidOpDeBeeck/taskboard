package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity addProject( @RequestParam( "title" ) String title,
            UriComponentsBuilder b
    )
    {
        Project project = taskBoard.createProject( title );

        UriComponents components = b.path( "projects/{id}" )
                .buildAndExpand( project.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.GET )
    public ResponseEntity<Project> getProject( @PathVariable( "projectId" ) String projectId )
    {
        return new ResponseEntity<>( taskBoard.getProjectById( projectId ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.PUT )
    public ResponseEntity updateProject( @PathVariable( "projectId" ) String projectId,
            @RequestParam( "title" ) String title
    )
    {
        Project project = taskBoard.getProjectById( projectId );
        project.setTitle( title );
        taskBoard.updateProject( project );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.DELETE )
    public ResponseEntity removeProject( @PathVariable( "projectId" ) String projectId )
    {
        taskBoard.removeProject( projectId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}/lanes", method = RequestMethod.POST )
    public ResponseEntity addLaneToProject( @PathVariable( "projectId" ) String projectId,
            @RequestParam( "title" ) String title,
            @RequestParam( "sequence" ) Integer sequence,
            @RequestParam( "completed" ) Boolean completed,
            UriComponentsBuilder b
    )
    {
        Lane lane = taskBoard.addLaneToProject( projectId, title, sequence, completed );

        UriComponents components = b.path( "lanes/{id}" )
                .buildAndExpand( lane.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/{projectId}/lanes/{laneId}", method = RequestMethod.DELETE )
    public ResponseEntity<Lane> removeLane( @PathVariable( "projectId" ) String projectId,
            @PathVariable( "laneId" ) String laneId
    )
    {
        taskBoard.removeLaneFromProject( projectId, laneId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}
