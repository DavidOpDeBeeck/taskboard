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

    //-------------------------------------------
    // region Project
    //-------------------------------------------

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity addProject( @RequestParam( "title" ) String title,
            UriComponentsBuilder b
    )
    {
        Project project = taskBoard.createProject( title );

        UriComponents components = b.path( "/projects/{id}" )
                .buildAndExpand( project.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<Project> getProject( @PathVariable( "id" ) String id )
    {
        return new ResponseEntity<>( taskBoard.getProjectById( id ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    public ResponseEntity updateProject( @PathVariable( "id" ) String id,
            @RequestParam( "title" ) String title
    )
    {
        Project project = taskBoard.getProjectById( id );
        project.setTitle( title );
        taskBoard.updateProject( project );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public ResponseEntity removeProject( @PathVariable( "id" ) String id )
    {
        taskBoard.removeProject( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Lane
    //-------------------------------------------

    @RequestMapping( value = "/{projectId}/lanes", method = RequestMethod.POST )
    public ResponseEntity addLaneToProject( @PathVariable( "projectId" ) String id,
            @RequestParam( "title" ) String title,
            @RequestParam( "sequence" ) Integer sequence,
            @RequestParam( "completed" ) Boolean completed,
            UriComponentsBuilder b
    )
    {
        Project project = taskBoard.getProjectById( id );
        Lane lane = taskBoard.addLaneToProject( project.getId(), title, sequence, completed );

        UriComponents components = b.path( "projects/{id}/lanes/{id}" )
                .buildAndExpand( project.getId(), lane.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/{projectId}/lanes/{laneId}", method = RequestMethod.GET )
    public ResponseEntity<Lane> getLane( @PathVariable( "projectId" ) String id,
            @PathVariable( "laneId" ) String laneId
    )
    {
        Lane lane = taskBoard.getLaneById( laneId );
        return new ResponseEntity<>( lane, HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}/lanes/{laneId}", method = RequestMethod.DELETE )
    public ResponseEntity<Lane> removeLane( @PathVariable( "projectId" ) String id,
            @PathVariable( "laneId" ) String laneId
    )
    {
        taskBoard.removeLaneFromProject( id, laneId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}/lanes/{laneId}", method = RequestMethod.PUT )
    public ResponseEntity updateLane( @PathVariable( "projectId" ) String id,
            @PathVariable( "laneId" ) String laneId,
            @RequestParam( "title" ) String title,
            @RequestParam( "sequence" ) Integer sequence,
            @RequestParam( "completed" ) Boolean completed
    )
    {
        Project project = taskBoard.getProjectById( id );
        Lane lane = taskBoard.getLaneById( laneId );

        lane.setTitle( title );
        lane.setSequence( sequence );
        lane.setCompleted( completed );

        taskBoard.updateLane( project, lane );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    //-------------------------------------------
    // endregion
    //-------------------------------------------

    //-------------------------------------------
    // region Task
    //-------------------------------------------

    @RequestMapping( value = "/{projectId}/lanes/{laneId}/tasks", method = RequestMethod.POST )
    public ResponseEntity addTaskToLane( @PathVariable( "projectId" ) String id,
            @PathVariable( "laneId" ) String laneId,
            @RequestParam( "title" ) String title,
            @RequestParam( "description" ) String description,
            @RequestParam( "assignee" ) String assignee,
            UriComponentsBuilder b
    )
    {
        Task task = taskBoard.addTaskToLane( laneId, title, description, assignee );

        UriComponents components = b.path( "projects/{id}/lanes/{id}/tasks/{id}" )
                .buildAndExpand( id, laneId, task.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/{projectId}/lanes/{laneId}/tasks/{taskId}", method = RequestMethod.GET )
    public ResponseEntity<Task> getTask( @PathVariable( "projectId" ) String id,
            @PathVariable( "laneId" ) String laneId,
            @PathVariable( "taskId" ) String taskId
    )
    {
        Task task = taskBoard.getTaskById( taskId );
        return new ResponseEntity<>( task, HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}/lanes/{laneId}/tasks/{taskId}", method = RequestMethod.DELETE )
    public ResponseEntity removeTask( @PathVariable( "projectId" ) String id,
            @PathVariable( "laneId" ) String laneId,
            @PathVariable( "taskId" ) String taskId
    )
    {
        taskBoard.removeTaskFromLane( laneId, taskId );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    //-------------------------------------------
    // endregion
    //-------------------------------------------

}
