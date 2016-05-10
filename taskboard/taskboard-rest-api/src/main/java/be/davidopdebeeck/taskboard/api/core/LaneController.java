package be.davidopdebeeck.taskboard.api.core;

import be.davidopdebeeck.taskboard.api.dto.LaneDTO;
import be.davidopdebeeck.taskboard.api.dto.TaskDTO;
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

@RestController
@RequestMapping( "/lanes/{laneId}" )
public class LaneController
{

    @Autowired
    TaskBoard taskBoard;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Lane> get( @PathVariable( "laneId" ) String laneId )
    {
        Lane lane = taskBoard.getLaneById( laneId );

        if (lane == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        return new ResponseEntity<>( lane, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity delete( @PathVariable( "laneId" ) String laneId )
    {
        Lane lane = taskBoard.getLaneById( laneId );

        if (lane == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        taskBoard.removeLane( laneId );

        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity update( @PathVariable( "laneId" ) String laneId, @RequestBody LaneDTO dto )
    {
        Lane lane = taskBoard.getLaneById( laneId );

        if (lane == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        lane.setTitle( dto.getTitle() );
        lane.setSequence( dto.getSequence() );
        lane.setCompleted( dto.isCompleted() );

        taskBoard.updateLane( lane );

        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/tasks", method = RequestMethod.POST )
    public ResponseEntity createTask( @PathVariable( "laneId" ) String laneId, @RequestBody TaskDTO dto, UriComponentsBuilder b )
    {
        if ( dto.getId() != null )
        {
            taskBoard.addTaskToLane( laneId, dto.getId() );
            return new ResponseEntity<Void>( HttpStatus.OK );
        }

        Task task = taskBoard.addTaskToLane( laneId, dto.getTitle(), dto.getDescription(), dto.getAssignee() );

        UriComponents components = b.path( "tasks/{id}" ).buildAndExpand( task.getId() );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( components.toUri() );

        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/tasks/{taskId}", method = RequestMethod.DELETE )
    public ResponseEntity deleteTask( @PathVariable( "laneId" ) String laneId, @PathVariable( "taskId" ) String taskId )
    {
        Lane lane = taskBoard.getLaneById( laneId );

        if (lane == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        Task task = taskBoard.getTaskById( taskId );

        if (task == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        taskBoard.removeTaskFromLane( laneId, taskId );

        return new ResponseEntity<>( HttpStatus.OK );
    }

}
