package be.davidopdebeeck.taskboard.api.auth;

import be.davidopdebeeck.taskboard.api.auth.dto.AuthRequestDTO;
import be.davidopdebeeck.taskboard.api.auth.dto.AuthResponseDTO;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.service.TaskBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/authenticate" )
public class AuthController
{

    @Autowired
    TaskBoard taskBoard;

    @Autowired
    AuthManager authManager;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<AuthResponseDTO> authenticate( @RequestBody AuthRequestDTO dto )
    {
        AuthResponseDTO response = new AuthResponseDTO();
        Project project = taskBoard.getProjectById( dto.getProjectId() );

        if ( dto.getPassword() != null && !dto.getPassword().isEmpty() && project.isPasswordValid( dto.getPassword() ) )
        {
            response.setToken( authManager.save( project.getId() ) );
            response.setSuccess( true );
        } else
        {
            response.setSuccess( false );
        }

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

}
