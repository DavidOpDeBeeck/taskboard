package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.dto.LaneDTO;
import be.davidopdebeeck.taskboard.dto.LaneUpdateDTO;
import be.davidopdebeeck.taskboard.dto.LaneWithTasksDTO;
import be.davidopdebeeck.taskboard.dto.TaskDTO;
import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Task;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<LaneWithTasksDTO> get( @PathVariable( "laneId" ) String laneId )
    {
        Lane lane = taskBoard.getLaneById( laneId );

        if (lane == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        LaneWithTasksDTO dto = new LaneWithTasksDTO();
        BeanUtils.copyProperties( lane, dto );

        return new ResponseEntity<>( dto, HttpStatus.OK );
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
    public ResponseEntity update( @PathVariable( "laneId" ) String laneId, @RequestBody LaneUpdateDTO dto )
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
        Lane lane = taskBoard.getLaneById(laneId);

        if ( dto.getId() != null )
        {
            Task task = taskBoard.getTaskById( dto.getId());
            lane.addTask(task);
            taskBoard.updateLane(lane);
            return new ResponseEntity<Void>( HttpStatus.OK );
        }

        Task task = taskBoard.createTask(dto.getTitle(), dto.getDescription(), dto.getAssignee());
        lane.addTask(task);
        taskBoard.updateLane(lane);

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

        lane.removeTask(task);
        taskBoard.updateLane(lane);

        return new ResponseEntity<>( HttpStatus.OK );
    }

}
