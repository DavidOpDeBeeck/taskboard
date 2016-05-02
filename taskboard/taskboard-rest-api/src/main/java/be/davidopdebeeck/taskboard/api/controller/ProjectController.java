package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.api.dto.LaneDTO;
import be.davidopdebeeck.taskboard.api.dto.ProjectDTO;
import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
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
    public ResponseEntity addProject( @RequestBody ProjectDTO dto, UriComponentsBuilder b )
    {
        String title = dto.getTitle();
        String password = dto.getPassword();

        if ( title.isEmpty() )
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );

        Project project;

        if ( !dto.isSecured() || password == null || password.isEmpty() )
            project = taskBoard.createProject( title );
        else
            project = taskBoard.createProject( title, password );

        UriComponents components = b.path( "projects/{id}" ).buildAndExpand( project.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.GET )
    public ResponseEntity<Project> getProject( @PathVariable( "projectId" ) String projectId )
    {
        return new ResponseEntity<>( taskBoard.getProjectById( projectId ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.PUT )
    public ResponseEntity updateProject( @PathVariable( "projectId" ) String projectId, @RequestBody ProjectDTO dto )
    {
        Project project = taskBoard.getProjectById( projectId );
        project.setTitle( dto.getTitle() );

        if ( ( project.isSecured() && dto.isSecured() ) || ( !project.isSecured() && dto.isSecured() ) )
        {
            if ( dto.getPassword() != null && !dto.getPassword().isEmpty() )
                project.setPassword( dto.getPassword() );
        } else if ( project.isSecured() )
        {
            project.setPassword( null );
        }

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
    public ResponseEntity addLaneToProject( @PathVariable( "projectId" ) String projectId, @RequestBody LaneDTO dto, UriComponentsBuilder b )
    {
        Lane lane = taskBoard.addLaneToProject( projectId, dto.getTitle(), dto.getSequence(), dto.isCompleted() );

        UriComponents components = b.path( "lanes/{id}" ).buildAndExpand( lane.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/{projectId}/lanes/{laneId}", method = RequestMethod.DELETE )
    public ResponseEntity<Lane> removeLaneFromProject( @PathVariable( "projectId" ) String projectId, @PathVariable( "laneId" ) String laneId )
    {
        taskBoard.removeLaneFromProject( projectId, laneId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}
