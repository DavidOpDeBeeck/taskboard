package be.davidopdebeeck.taskboard.api.controller;

import be.davidopdebeeck.taskboard.dto.*;
import be.davidopdebeeck.taskboard.api.security.SecurityManager;
import be.davidopdebeeck.taskboard.core.Lane;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/projects" )
public class ProjectController {
    @Autowired
    TaskBoard taskBoard;

    @Autowired
    SecurityManager securityManager;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Collection<ProjectDTO>> get() {
        Collection<ProjectDTO> dtoList = taskBoard.getAllProjects().stream().map( p -> {
            ProjectDTO dto = new ProjectDTO();
            BeanUtils.copyProperties( p, dto );
            return dto;
        } ).collect( Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity create( @RequestBody ProjectUpdateDTO dto, UriComponentsBuilder b ) {
        String title = dto.getTitle();
        String password = dto.getPassword();

        if (title.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Project project;

        if (dto.isSecured() && password != null && !password.isEmpty())
            project = taskBoard.createProject(title, password);
        else
            project = taskBoard.createProject(title);


        UriComponents components = b.path("projects/{id}").buildAndExpand(project.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(components.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.GET )
    public ResponseEntity<ProjectWithLanesDTO> get( @PathVariable( "projectId" ) String projectId ) {
        Project project = taskBoard.getProjectById(projectId);

        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ProjectWithLanesDTO dto = new ProjectWithLanesDTO();
        BeanUtils.copyProperties( project, dto );

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.PUT )
    public ResponseEntity update( @PathVariable( "projectId" ) String projectId, @RequestBody ProjectUpdateDTO dto ) {
        Project project = taskBoard.getProjectById(projectId);

        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        project.setTitle(dto.getTitle());

        String password = dto.getPassword();

        if (dto.isSecured() && password != null && !password.isEmpty())
            project.setPassword(password);
        else
            project.setPassword(null);

        taskBoard.updateProject(project);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.DELETE )
    public ResponseEntity delete( @PathVariable( "projectId" ) String projectId ) {
        Project project = taskBoard.getProjectById(projectId);

        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        taskBoard.removeProject(projectId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( value = "/{projectId}/lanes", method = RequestMethod.POST )
    public ResponseEntity createLane( @PathVariable( "projectId" ) String projectId, @RequestBody LaneUpdateDTO dto, UriComponentsBuilder b ) {
        Project project = taskBoard.getProjectById(projectId);

        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Lane lane = taskBoard.createLane(dto.getTitle(), dto.getSequence(), dto.isCompleted());
        project.addLane(lane);
        taskBoard.updateProject(project);

        UriComponents components = b.path("lanes/{id}").buildAndExpand(lane.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(components.toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping( value = "/{projectId}/lanes/{laneId}", method = RequestMethod.DELETE )
    public ResponseEntity<Lane> deleteLane( @PathVariable( "projectId" ) String projectId, @PathVariable( "laneId" ) String laneId ) {
        Project project = taskBoard.getProjectById(projectId);

        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Lane lane = taskBoard.getLaneById(laneId);

        if (lane == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        project.removeLane(lane);
        taskBoard.updateProject(project);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
